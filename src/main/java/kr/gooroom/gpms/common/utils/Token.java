package kr.gooroom.gpms.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Token {

	private final String salt;
	private final String issuer;
	private final long expInterval;

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
	 */
	public String genToken(String clientIp, String clientId) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    long nowMillis = cal.getTimeInMillis() + expInterval;
	    Date now = new Date(nowMillis);
	    logger.debug("now={}", now);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt );
		Key key = new SecretKeySpec(apiKeySecretBytes, HmacAlgorithms.HMAC_SHA_256.toString());
		JwtBuilder builder = Jwts.builder().id(clientId).issuer(issuer).expiration(now).signWith(key);
	    logger.debug("TOKEN CREATED client_id={}", clientId);
	    return builder.compact();
	}
	
    /**
     * 토큰을 파싱한다.
     * 
     * @param String token, String clientIp
     * @return String
	 */
	public String parseToken(String token, String clientIp) {
		SecretKey secretKey = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary( salt));
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	    return claims.getId();
	}
}
