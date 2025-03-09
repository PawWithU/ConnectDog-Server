package com.pawwithu.connectdog.domain.oauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OidcPublicKeyResponse {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}