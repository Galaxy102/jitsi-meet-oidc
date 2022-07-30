package de.galaxy102.jitsi.jwt;


import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Jitsi User Data structure as per <a href="https://github.com/jitsi/lib-jitsi-meet/blob/master/doc/tokens.md#token-structure">Jitsi documentation</a>
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@AllArgsConstructor
public class JitsiJwtUserData {

    /**
     * Unique user identifier
     */
    @NonNull
    @NotBlank
    private final String id;

    /**
     * Display name
     */
    @NonNull
    @NotBlank
    private final String name;

    /**
     * Email address, usage unknown
     */
    @NonNull
    private final String email;

    /**
     * User image
     */
    @NonNull
    private final String avatar;

    /**
     * Transform to JWT data map
     * <p>
     * Compatible with {@link io.smallrye.jwt.build.Jwt#claim(String, Object)}
     *
     * @return Mapped user data
     */
    Map<String, Map<String, String>> forJwt() {
        return Map.of(
                JitsiJwtClaims.CONTEXT_USER, Map.of(
                        JitsiJwtClaims.CONTEXT_USER_ID, this.id,
                        JitsiJwtClaims.CONTEXT_USER_NAME, this.name,
                        JitsiJwtClaims.CONTEXT_USER_EMAIL, this.email,
                        JitsiJwtClaims.CONTEXT_USER_AVATAR, this.avatar
                )
        );
    }
}
