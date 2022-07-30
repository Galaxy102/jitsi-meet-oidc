# jitsi-meet-oidc

This application allows Jitsi to authenticate using OpenID Connect.

## How to run

The normal use case would be to ship this application together with your Jitsi cluster behind a reverse proxy, for example with [docker-jitsi-meet](https://github.com/jitsi/docker-jitsi-meet).

You should set the following environment variables on the Jitsi containers (if you use the official builds):

```yaml
JWT_APP_ID=jitsi-oidc # or another value
JWT_APP_SECRET=       # a random string of at least 32 chars
TOKEN_AUTH_URL=http://<HOST-OF-JITSI>:8080/.auth/authorize/{room} # Link to below service endpoint (best: configure it to run behind a reverse proxy on the same domain as your Jitsi with TLS)
```

You would then append your docker-jitsi-meet setup with the following service:

```yaml
jitsi-oidc:
  image: galaxy102/jitsi-meet-oidc
  ports:
    - "8080:8080"
  environment:
    # Required:
    JITSI_BASE_URL:                   # URL to your Jitsi instance, e.g. https://meet.example.com
    JITSI_JWT_SECRET:                 # Same value as JWT_APP_SECRET
    JITSI_OIDC_REALM_URL:             # Path to your OpenID realm
    JITSI_OIDC_CLIENT_ID:             # OpenID client ID
    JITSI_OIDC_CLIENT_SECRET:         # OpenID client secret
    # Optional:
    JITSI_JWT_APPID: jitsi-oidc       # Same value as JWT_APP_ID, can be omitted when using the default
    JITSI_JWT_VALIDITY: 30            # Validity of emitted JWT in seconds -> Time until the redirect must have happened
    JITSI_OIDC_AVATAR_CLAIM: picture  # Claim containing link to user avatar in OpenID token
    JITSI_AUTH_BASE_PATH: /.auth/     # Base path of this application to allow running on the same URL as Jitsi behind reverse proxies
```

This config is insecure as it uses an unencrypted port to perform the OpenID flow, even though your identity provider should use TLS. Please see this only as an example and use a reverse proxy like [Træfik Proxy](https://traefik.io/traefik/) to add TLS.

# Developer notes

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/jitsi-meet-oidc-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- OpenID Connect Client ([guide](https://quarkus.io/guides/security-openid-connect-client)): Get and refresh access tokens from OpenID Connect providers
- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
