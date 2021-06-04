package com.union.design.auth.jwt;

import io.jsonwebtoken.io.Decoders;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * Auth related configurations.
 */
@Configuration
public class JwtTokenConfig {

    
    /**
     * secret key.
     */
    @Value("${jwt.token.secret.key:}")
    private String secretKey;
    
    /**
     * secret key byte array.
     */
    private byte[] secretKeyBytes;
    
    /**
     * Token validity time(seconds).
     */
    @Value("${jwt.token.expire.seconds:1800}")
    private long tokenValidityInSeconds;
    
    public byte[] getSecretKeyBytes() {
        if (secretKeyBytes == null) {
            secretKeyBytes = Decoders.BASE64.decode(secretKey);
        }
        return secretKeyBytes;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

}