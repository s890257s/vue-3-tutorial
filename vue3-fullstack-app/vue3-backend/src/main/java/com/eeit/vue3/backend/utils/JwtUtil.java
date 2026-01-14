package com.eeit.vue3.backend.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT 工具類別
 * <p>
 * 負責 JWT Token 的生成、解析、驗證等核心功能。
 * 使用 JJWT (Java JWT) 套件實作。
 */
@Component
public class JwtUtil {

	public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
		this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	// 計算用私鑰物件
	private final SecretKey secretKey;

	// JWT 有效時間，單位為秒
	private final long EXPIRATION_IN_SECONDS = 60 * 60;

	/**
	 * 產生 JWT Token
	 *
	 * @param userId 使用者 ID (將作為 Subject)
	 * @param role   使用者角色 (將存入 calim "role")
	 * @return 簽名後的 JWT 字串
	 */
	public String generateToken(String userId, String role) {
		return Jwts.builder() // 使用 builder 模式設定 token
				.subject(userId) // 設定主題(subject)，通常放唯一識別的 User ID
				.claim("role", role) // 設定自定義的 claim，可隨需求增加，但建議不要存放太多資料
				// .claim("role", List.of("user", "admin")) // 也可存放物件
				.issuedAt(new Date()) // 設定發行時間
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_IN_SECONDS * 1000)) // 設定到期時間
				.signWith(secretKey) // 使用私鑰簽名
				.compact(); // 產生 token
	}

	/**
	 * 解析並驗證 JWT
	 * <p>
	 * 會驗證簽名是否正確以及 Token 是否過期。
	 *
	 * @param token JWT 字串
	 * @return 解析後的 Claims 物件 (包含 payload 資料)
	 * @throws io.jsonwebtoken.JwtException 當驗證失敗時拋出各種子類別例外
	 */
	public Claims getClaims(String token) {
		return Jwts.parser() // 使用 parser() 取得解析器
				.verifyWith(secretKey) // 設定解密用密鑰
				.build() // 建立解析器
				.parseSignedClaims(token) // 解析 token
				.getPayload(); // 取得解析後結果
	}

	/**
	 * 取得 Token 主題 (Subject)，通常存放 User ID
	 *
	 * @param token JWT 字串
	 * @return User ID
	 */
	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * 取得自定義的 claim
	 */
	public String getValue(String token, String key) {
		return (String) getClaims(token).get(key);
	}

	/**
	 * 驗證 Token 是否合法
	 * <p>
	 * 嘗試解析 Token，若無拋出例外則視為合法。
	 *
	 * @param token JWT 字串
	 * @return true 若合法，否則拋出例外 (不回傳 false，而是直接拋錯)
	 */
	public Boolean isTokenValid(String token) {
		getSubject(token); // 若 token 有任何例外，則由 jjwt 套件直接拋出錯誤。
		return true; // 能走到回傳表示驗證通過，token 合法
	}

}
