package com.pawwithu.connectdog.domain.oauth.service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pawwithu.connectdog.domain.oauth.dto.response.AppleInfoResponse;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeUnit;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Component
public class OidcJwtDecoder {

    private static final String APPLE_OIDC_URL = "https://appleid.apple.com/auth/keys";
    private static final String APPLE_ISSUER = "https://appleid.apple.com";
    private final JwkProvider jwkProvider;

    public OidcJwtDecoder() throws Exception {
        this.jwkProvider = new JwkProviderBuilder(new URL(APPLE_OIDC_URL))
                .cached(10, 24, TimeUnit.HOURS) // 최대 10개 키를 24시간 캐싱
                .rateLimited(10, 1, TimeUnit.MINUTES) // 1분당 최대 10개 요청 제한
                .build();
    }

    public String getKidFromUnsignedTokenHeader(String token, String clientId) { // id_token 헤더에서 kid 값 추출 및 issuer/audience 검증
        try {
            DecodedJWT jwt = JWT.decode(token);

            // 1) Issuer 검증 (애플에서 발급된 토큰인지 확인)
            if (!jwt.getIssuer().equals(APPLE_ISSUER)) {
                log.info("Invalid id_token issuer: " + jwt.getIssuer());
                throw new BadRequestException(INVALID_ID_TOKEN_ISSUER);
            }

            // 2) Audience 검증 (내 앱에서 발급된 토큰인지 확인)
            if (!jwt.getAudience().contains(clientId)) {
                log.info("Invalid id_token audience: " + jwt.getAudience());
                throw new BadRequestException(INVALID_ID_TOKEN_AUDIENCE);
            }

            return jwt.getKeyId(); // kid 반환
        } catch (Exception e) {
            throw new BadRequestException(APPLE_VALIDATED_ERROR);
        }
    }

    public AppleInfoResponse getOidcTokenBody(String token) { // 공개 키를 사용하여 id_token 서명 검증 및 디코딩
        try {
            DecodedJWT jwt = JWT.decode(token);

            // 1) kid 값으로 공개 키 가져오기
            Jwk jwk = jwkProvider.get(jwt.getKeyId());
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

            // 2) 알고리즘 검증 및 id_token 서명 검증
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            jwt = JWT.require(algorithm)
                    .withIssuer(APPLE_ISSUER) // 애플에서 발급된 토큰인지 재검증
                    .build()
                    .verify(token);

            // 3) 사용자 정보 추출
            String sub = jwt.getSubject();

            return new AppleInfoResponse(sub);
        } catch (Exception e) {
            throw new BadRequestException(APPLE_VALIDATED_ERROR);
        }
    }
}
