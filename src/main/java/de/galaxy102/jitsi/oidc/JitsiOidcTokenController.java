package de.galaxy102.jitsi.oidc;

import de.galaxy102.jitsi.jwt.JitsiJwtProvider;
import de.galaxy102.jitsi.jwt.JitsiJwtUserData;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Controller to emit Jitsi JWTs
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@Path("/authorize")
@Authenticated
public class JitsiOidcTokenController {
    @Inject
    protected JitsiOidcConfig jitsiOidcConfig;

    @Inject
    @IdToken
    protected JsonWebToken idToken;

    @Inject
    protected JitsiJwtProvider jitsiJwtProvider;

    /**
     * Perform the Token conversion and link back to Jitsi.
     * <p>
     * You wonder where OIDC happens? See the {@link Authenticated} annotation above ;)
     *
     * @param jitsiRoomId Jitsi room to authorize
     * @return Redirect to Jitsi with JWT set
     */
    @GET
    @Path("/{jitsiRoomId}")
    @Produces(MediaType.TEXT_HTML)
    public Response returnWithToken(@PathParam("jitsiRoomId") String jitsiRoomId) {
        JitsiJwtUserData jitsiUserInfo = new JitsiJwtUserData(
                this.idToken.getSubject(),
                (String) this.idToken.claim(Claims.full_name).or(() -> idToken.claim("name")).orElse(idToken.getName()),
                (String) this.idToken.claim(Claims.email).orElseThrow(() -> new JitsiOidcTokenException("Missing email claim.")),
                (String) this.idToken.claim(this.jitsiOidcConfig.avatarClaim()).orElse("")
        );
        String outJwt = this.jitsiJwtProvider.buildToken(jitsiRoomId, jitsiUserInfo);
        return Response.temporaryRedirect(
                URI.create(this.jitsiOidcConfig.jitsiUrl() + "/" + jitsiRoomId + "?jwt=" + outJwt)
        ).build();
    }
}
