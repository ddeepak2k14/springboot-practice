package com.example.demo.service;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenGeneration {
	String password;

	public String generateJWT() {
		try {
			PrivateKey secretKey = getKeyFromCertificateFile();

			Map<String, Object> map = new HashMap<>();
			map.put("alg", "RS256");
			map.put("typ", "jwt");
			/*
			 * iss (issuer): Issuer of the JWT sub (subject): Subject of the JWT (the user)
			 * aud (audience): Recipient for which the JWT is intended iat (issued at time):
			 * Time at which the JWT was issued; can be used to determine age of the JWT exp
			 * (expiration time): Time after which the JWT expires
			 */
			String jwt = Jwts.builder().setHeader(map).claim("iss", "deepak")
					.claim("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).claim("ver", "1.0")
					.claim("exp", LocalDateTime.now().plusHours(7 * 24).toEpochSecond(ZoneOffset.UTC))
					.claim("sub", "sub").claim("aud", "aud").signWith(SignatureAlgorithm.RS256, secretKey).compact();

			System.out.println(jwt);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	private PrivateKey getKeyFromCertificateFile() {
		PrivateKey privateKey = null;
		try {
			/*
			 * Read pfx i,e pkcs12 file that contain the public key file (SSL certificate file) 
			 * and the associated private key file
			 * 
			 */
			KeyStore ks = KeyStore.getInstance("pkcs12");
			ks.load(new FileInputStream("filename"), password.toCharArray());
			Enumeration<String> aliases = ks.aliases();
			String alias = aliases.nextElement();
			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
					new KeyStore.PasswordProtection(password.toCharArray()));
			privateKey = privateKeyEntry.getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKey;

	}

	public Claims decodeJWT(String jwt) {
		PrivateKey secretKey = getKeyFromCertificateFile();
		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
		return claims;
	}

}
