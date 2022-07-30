package de.galaxy102.jitsi.jwt;

/**
 * Claim strings as per <a href="https://github.com/jitsi/lib-jitsi-meet/blob/master/doc/tokens.md#token-structure">Jitsi documentation</a>
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
public final class JitsiJwtClaims {
    public static final String ROOM = "room";
    public static final String CONTEXT = "context";
    public static final String CONTEXT_USER = "user";
    public static final String CONTEXT_USER_AVATAR = "avatar";
    public static final String CONTEXT_USER_EMAIL = "email";
    public static final String CONTEXT_USER_ID = "id";
    public static final String CONTEXT_USER_NAME = "name";
}
