package de.galaxy102.jitsi.oidc;

/**
 * Exception when an OpenID Connect request fails due to invalid data
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
public class JitsiOidcTokenException extends RuntimeException {
    /**
     * Exception when an OpenID Connect request fails due to invalid data
     *
     * @param message Failure reason
     */
    public JitsiOidcTokenException(String message) {
        super(message);
    }
}
