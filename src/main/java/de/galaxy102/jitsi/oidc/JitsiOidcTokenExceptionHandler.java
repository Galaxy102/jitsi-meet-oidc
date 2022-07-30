package de.galaxy102.jitsi.oidc;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Handle {@link JitsiOidcTokenException}
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@Provider
public class JitsiOidcTokenExceptionHandler implements ExceptionMapper<JitsiOidcTokenException> {
    /**
     * Show an error page for invalid OIDC ID Tokens
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(JitsiOidcTokenException exception) {
        return Response
                .status(Response.Status.FORBIDDEN)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(exception.getMessage() + " Please contact your IDM administrator.")
                .build();
    }
}
