package de.galaxy102.jitsi.oidc;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

/**
 * Configuration parameters for {@link JitsiOidcTokenController}
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@ConfigMapping(prefix = "jitsi.oidc")
public interface JitsiOidcConfig {

    /**
     * OpenID ID Token claim containing image link.
     */
    @WithName("avatar-claim")
    @WithDefault("picture")
    String avatarClaim();

    /**
     * Full URL to Jitsi instance.
     * Used to link back to url + /room
     */
    @WithName("return-url")
    java.net.URL jitsiUrl();
}
