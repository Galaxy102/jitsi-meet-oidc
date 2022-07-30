package de.galaxy102.jitsi.oidc;

import de.galaxy102.jitsi.jwt.JitsiJwtClaims;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.impl.jose.JWT;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Demo return path, used only in dev profile to view generated JWT
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@Path("/validate/{jitsiRoomId}")
@IfBuildProfile("dev")
@Authenticated
public class JitsiOidcTokenValidatorController {

    /**
     * Read JWT out loud.
     *
     * @param jitsiRoomId Jitsi room to authorize
     * @param jwt         Incoming JWT
     * @return JWT as text
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response returnWithToken(@PathParam("jitsiRoomId") String jitsiRoomId, @QueryParam("jwt") String jwt, @Context UriInfo uriInfo) {
        JsonObject data = JWT.parse(jwt);
        if (!data.getJsonObject("payload").getString(JitsiJwtClaims.ROOM).equals(jitsiRoomId)) {
            return Response.status(Response.Status.UNAUTHORIZED.getStatusCode(), "Room does not match URL").build();
        }
        return Response.ok(data.toString()).build();
    }
}
