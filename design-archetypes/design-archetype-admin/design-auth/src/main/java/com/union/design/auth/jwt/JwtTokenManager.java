/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.union.design.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * JWT token manager.
 *
 */
@Component
public class JwtTokenManager {
    
    private static final String AUTHORITIES_KEY = "auth";
    
    @Autowired
    private JwtTokenConfig config;
    
    /**
     * Create token.
     *
     * @param authentication auth info
     * @return token
     */
    public String createToken(Authentication authentication) {
        return createToken(authentication.getName());
    }
    
    /**
     * Create token.
     *
     * @param username auth info
     * @return token
     */
    public String createToken(String username) {
        
        long now = System.currentTimeMillis();
        
        Date validity = new Date(now + config.getTokenValidityInSeconds() * 1000L);
        
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder().setClaims(claims).setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(config.getSecretKeyBytes()), SignatureAlgorithm.HS256).compact();
    }
    
    /**
     * Get auth Info.
     *
     * @param token token
     * @return auth info
     */
    public JwtAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(config.getSecretKeyBytes()).build()
                .parseClaimsJws(token).getBody();
        
        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
        
        User principal = new User(claims.getSubject(), "", authorities);
        return new JwtAuthenticationToken(principal, "", authorities);
    }
    
    /**
     * validate token.
     *
     * @param token token
     */
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(config.getSecretKeyBytes()).build().parseClaimsJws(token);
    }
    
}