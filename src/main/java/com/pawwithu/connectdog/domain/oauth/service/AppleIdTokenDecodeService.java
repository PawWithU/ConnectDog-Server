package com.pawwithu.connectdog.domain.oauth.service;

import com.pawwithu.connectdog.domain.oauth.api.AppleOidcKeyClient;
import com.pawwithu.connectdog.domain.oauth.dto.response.AppleInfoResponse;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pawwithu.connectdog.error.ErrorCode.APPLE_VALIDATED_ERROR;
import static com.pawwithu.connectdog.error.ErrorCode.NOT_FOUND_APPLE_PUBLIC_KEY;

@Service
@RequiredArgsConstructor
public class AppleIdTokenDecodeService {

    private final AppleOidcKeyClient appleOidcKeyClient;

    private final OidcJwtDecoder oidcJwtDecoder;
    @Value("${oauth.apple.iss}")
    private String iss;

    @Value("${oauth.apple.client-id}")
    private String clientId;

    @Transactional
    public AppleInfoResponse getPayloadFromIdToken(final String token) {
        try {
            // 1) JWT 헤더에서 kid 값 추출 (issuer 및 audience 검증 포함)
            final String kid = oidcJwtDecoder.getKidFromUnsignedTokenHeader(token, clientId);

            // 2) 애플의 공개 키 가져오기
            final var applePublicKeyList = appleOidcKeyClient.getAppleOidcOpenKeys();
            final var oidcPublicKey = applePublicKeyList.getKeys().stream()
                    .filter(o -> o.getKid().equals(kid))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException(NOT_FOUND_APPLE_PUBLIC_KEY));

            // 3) id_token 검증 및 디코딩
            return oidcJwtDecoder.getOidcTokenBody(token);
        } catch (Exception e) {
            throw new BadRequestException(APPLE_VALIDATED_ERROR);
        }
    }
}
