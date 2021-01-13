package kr.gooroom.gpms.common.utils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {

	private String salt;
	private String issuer;
	private long expInterval;

	private static final Logger logger = LoggerFactory.getLogger(Token.class);
	
	
	public Token(String salt, String issuer, String expInterval) {
		this.salt = salt;
	    this.issuer = issuer;
	    this.expInterval = Long.parseLong(expInterval);
	}
	
    /**
     * 토큰을 생성한다.
     * 
     * @param String clientIp, String clientId
     * @return String
     * @throws JwtException
     */   
	public String genToken(String clientIp, String clientId) throws Exception {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    long nowMillis = cal.getTimeInMillis() + expInterval;
	    Date now = new Date(nowMillis);
	    logger.debug("now={}", now);

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(clientIp + salt);
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt);
	    Key key = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    
	    JwtBuilder builder = Jwts.builder().setId(clientId)
	                                .setIssuer(issuer)
	                                .setExpiration(now)
	                                .signWith(signatureAlgorithm, key);
	 
	    logger.debug("TOKEN CREATED client_id={}", clientId);
	    return builder.compact();
	}
	
    /**
     * 토큰을 파싱한다.
     * 
     * @param String token, String clientIp
     * @return String
     * @throws JwtException
     */   
	public String parseToken(String token, String clientIp) throws Exception {
		//logger.info("parsing ip={}", clientIp);
		/*
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(clientIp + salt))
	       .parseClaimsJws(token).getBody();
	    */
	    Claims claims = Jwts.parser()         
	 	       .setSigningKey(DatatypeConverter.parseBase64Binary(salt))
	 	       .parseClaimsJws(token).getBody();
	    
	    //logger.debug("ID: " + claims.getId());
	    //logger.debug("Issuer: " + claims.getIssuer());
	    //logger.debug("Expiration: " + claims.getExpiration());
	    
	    return claims.getId(); //clientId
	}
}
