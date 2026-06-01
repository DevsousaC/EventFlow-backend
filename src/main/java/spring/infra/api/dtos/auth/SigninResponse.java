package spring.infra.api.dtos.auth;

public record SigninResponse(
    String accessToken,
    String refreshToken,
    Long expiresIn
) {}
