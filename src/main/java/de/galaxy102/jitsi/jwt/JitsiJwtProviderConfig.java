package de.galaxy102.jitsi.jwt;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Configuration parameters for {@link JitsiJwtProvider}
 *
 * @author Konstantin Köhring &lt;konsti@galaxy102.de&gt;
 */
@ConfigMapping(prefix = "jitsi.jwt")
public interface JitsiJwtProviderConfig {

    /**
     * Validity of JWT to generate in seconds.
     * Possible values are 1..60 with a default of 30
     */
    @WithName("validity")
    @WithDefault("30")
    @Min(1)
    @Max(value = 60, message = "Setting JWT validity over 60 seconds is insecure.")
    Integer validity();

    /**
     * Expected Jitsi App ID.
     * Should be negotiated with Jitsi instance, defaults to jitsi-oidc
     */
    @WithName("app-id")
    @WithDefault("jitsi-oidc")
    String appId();

    /**
     * Shared JWT sign secret.
     * Should be negotiated with Jitsi instance
     */
    @WithName("secret")
    @Size(min = 32, message = "JWT secret must be at least 32 chars long")
    String secret();

    /**
     * Full URL to Jitsi instance.
     * Used to provide the correct subject
     */
    @WithName("subject-url")
    java.net.URL jitsiUrl();
}
