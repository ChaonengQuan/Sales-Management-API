package com.chaonengquan.service;


import com.chaonengquan.model.Role;
import com.chaonengquan.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JWTService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECRET_KEY = System.getProperty("secret.key");
    private final String ISSUER = "com.chaonengquan";
    private final long EXPIRATION_TIME = 3600 * 1000;                  //1 hour == 3600 seconds

    /**
     * 1. Decide signature algorithm
     * 2. Hard code secret key first, later user VM option to pass in the key
     * 3. Sign JWT with secret key
     * 4. Organized out payload: Claims (a map with some predefined keys, also can add some key)
     * 5. Set claims JWT api
     * 6. Generate the token
     *
     * @param user
     * @return
     */
    public String generateToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claim = Jwts.claims();
        claim.setId(String.valueOf(user.getId()));
        claim.setSubject(user.getName());   //based on pref
        claim.setIssuedAt(new Date(System.currentTimeMillis()));
        claim.setIssuer(ISSUER);
        claim.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        Set<Role> roles = user.getRoles();
        String allowedReadResource = roles.stream().filter(role -> role.isAllowedRead()).map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
        String allowedCreateResource = roles.stream().filter(role -> role.isAllowedCreate()).map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
        String allowedUpdateResource = roles.stream().filter(role -> role.isAllowedUpdate()).map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
        String allowedDeleteResource = roles.stream().filter(role -> role.isAllowedDelete()).map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
        //String allowedResource = roles.stream().map(role -> role.getAllowedResource()).collect(Collectors.joining(","));

        claim.put("allowedReadResource", allowedReadResource.replaceAll(",$", ""));     //regex to remove the last comma
        claim.put("allowedCreateResource", allowedCreateResource.replaceAll(",$", ""));
        claim.put("allowedUpdateResource", allowedUpdateResource.replaceAll(",$", ""));
        claim.put("allowedDeleteResource", allowedDeleteResource.replaceAll(",$", ""));
        //claim.put("allowedResource", allowedResource);
        //logger.info("======, allowedResource = {}", allowedResource);
        logger.info("======, allowedReadResource = {}", allowedReadResource);
        logger.info("======, allowedCreateResource = {}", allowedReadResource);
        logger.info("======, allowedUpdateResource = {}", allowedReadResource);
        logger.info("======, allowedDeleteResource = {}", allowedReadResource);

        //set the JWT claim
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claim).signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        String generatedToken = jwtBuilder.compact();
        return generatedToken;
    }

    public Claims decryptJwtToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))     //convert to byte array
                .parseClaimsJws(token).getBody();

        logger.debug("Claims: " + claims.toString());
        return claims;
    }


    public boolean hasTokenExpired(String token) {
        boolean hasExpiredFlag = true;

        try {
            Claims claims = decryptJwtToken(token);
            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();
            hasExpiredFlag = tokenExpirationDate.before(todayDate);
        }catch (ExpiredJwtException ex){
            logger.error("ExpiredJwtException is thrown when hasTokenExpired(token) is called, error={}", ex.getMessage());
        }

        return hasExpiredFlag;
    }

    

}
