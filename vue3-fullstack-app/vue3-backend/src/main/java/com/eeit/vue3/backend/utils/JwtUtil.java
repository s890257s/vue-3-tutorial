package com.eeit.vue3.backend.utils;

import java.nio.charset.StandardCharsets;
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

	/**
	 * 建構子 (Constructor)
	 * <p>
	 * 這裡利用 Spring 的依賴注入 (@Value) 功能，將設定檔中定義的密鑰字串注入進來。
	 * 這樣做的好處是可以將機密的 Secret Key 與程式碼分離，避免硬編碼 (Hard Code)。
	 * 
	 * @param jwtSecret 設定檔 (如 application.properties) 中的 "jwt.secret" 屬性值。
	 *                  注意：為了安全性，這個字串的長度應該足夠長 (建議至少 256 bits / 32 bytes)，
	 *                  否則在產生 HMAC-SHA 簽名時可能會被認為不夠安全。
	 */
	public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
		// 將注入的密鑰字串轉換為 byte 陣列
		// Keys.hmacShaKeyFor 是 JJWT 提供的輔助方法，它會根據 byte 陣列的長度
		// 自動選擇並產生適合的 HMAC-SHA 演算法 (如 HS256, HS384, HS512) 的 SecretKey 物件。
		// 這個 secretKey 之後會被用來對 JWT 進行簽名 (Sign) 和驗證 (Verify)。
		this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	// 計算用私鑰物件 (Cryptographic Key)
	// 用於 HMAC-SHA 演算法的簽名與驗證，必須妥善保存，不可外洩。
	private final SecretKey secretKey;

	// JWT 有效時間 (Expiration Time)
	// 設定 Token 的存活時間，單位為秒。
	// 這裡設定為 1 小時 (60 * 60 秒)。
	// 實務上建議 Token 效期不宜過長，以降低 Key 外洩後的風險。
	private final long EXPIRATION_IN_SECONDS = 60 * 60;

	/**
	 * 產生 JWT Token (Create/Sign Token)
	 * <p>
	 * 建立一個包含使用者資訊、發行時間、到期時間的 JWT 字串。
	 * 這個字串會被簽名，確保內容不被竄改。
	 *
	 * @param userId 使用者 ID (將作為 Subject，即 Token 的主體身分)
	 * @param role   使用者角色 (將存入自定義 claim "role" 中)
	 * @return 簽名後的 JWT 字串 (Base64Url 編碼)
	 */
	public String generateToken(String userId, String role) {
		return Jwts.builder() // 使用 JJWT 的 builder 模式來建構 Token
				.subject(userId) // 設定標準聲明 "sub" (Subject)：通常放唯一識別的 User ID
				.claim("role", role) // 設定自定義聲明 (Claims)：這裡存放角色資訊，可隨需求增加 (例如 email, username)
				// .claim("role", List.of("user", "admin")) // Value 也可以是複雜物件 (如 List)，會被序列化為
				// JSON array
				.issuedAt(new Date()) // 設定標準聲明 "iat" (Issued At)：Token 的發行時間
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_IN_SECONDS * 1000)) // 設定標準聲明 "exp"
																									// (Expiration
																									// Time)：設定到期時間
																									// (當前時間 + 有效秒數)
				.signWith(secretKey) // 設定簽名：使用我們先前準備好的 SecretKey 進行 HMAC-SHA 簽名
				.compact(); // 壓縮並產生最終的 JWT 字串 (Header.Payload.Signature)
	}

	/**
	 * 解析並驗證 JWT (Parse & Verify Token)
	 * <p>
	 * 此方法會檢查 Token 的簽名是否合法 (是否由本系統發出)，以及是否過期。
	 * 若驗證失敗，會拋出 Runtime Exception。
	 *
	 * @param token JWT 字串
	 * @return 解析後的 Claims 物件 (包含 Payload 中的所有資料，如 subject, expiration, custom
	 *         claims)
	 * @throws io.jsonwebtoken.JwtException 當驗證失敗時拋出各種子類別例外：
	 *                                      - ExpiredJwtException: Token 已過期
	 *                                      - UnsupportedJwtException: 不支援的 Token 格式
	 *                                      - MalformedJwtException: Token 格式錯誤
	 *                                      (被竄改或損壞)
	 *                                      - SignatureException: 簽名驗證失敗 (使用了錯誤的 Key
	 *                                      或 Token 內容被竄改)
	 *                                      - IllegalArgumentException: Token 字串為空或
	 *                                      null
	 */
	public Claims getClaims(String token) {
		return Jwts.parser() // 使用 parser() 取得解析器 Builder
				.verifyWith(secretKey) // 設定驗證簽名用的密鑰 (必須與產生 Token 時的密鑰相同)
				.build() // 建立執行緒安全的 JwtParser
				.parseSignedClaims(token) // 開始解析：驗證簽名並解析 Claims (此步驟若失敗會拋出異常)
				.getPayload(); // 取得解析後的 Payload (Claims) 本體
	}

	/**
	 * 取得 Token 主題 (Subject)
	 * <p>
	 * 這是包裝好的便利方法，直接從 Token 中取出 "sub" 欄位 (通常是 User ID)。
	 *
	 * @param token JWT 字串
	 * @return User ID (Subject)
	 */
	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * 取得自定義的 claim 資料
	 * <p>
	 * 從 Token Payload 中根據 key 取出對應的值。
	 *
	 * @param token JWT 字串
	 * @param key   Claim 的鍵名 (例如 "role")
	 * @return該 Claim 的值 (轉型為 String)
	 */
	public String getValue(String token, String key) {
		return (String) getClaims(token).get(key);
	}

	/**
	 * 驗證 Token 是否合法
	 * <p>
	 * 通過嘗試解析 Token 來判斷其有效性。
	 * JJWT 在解析時若遇到過期、簽名錯誤或格式錯誤，會直接拋出 Runtime Exception。
	 * 因此這裡使用 try-catch 機制 (或依賴上層 Global Exception Handler) 來處理。
	 * <p>
	 * 在這個實作中，我們假設若 getSubject() 能成功執行不拋錯，代表 Token 驗證通過。
	 * (註：實際上若發生錯誤，getSubject 會拋出例外，此方法不會回傳 false，而是中斷。
	 * 若需要回傳 boolean，應在此處加入 try-catch 包裹 getSubject 呼叫)
	 *
	 * @param token JWT 字串
	 * @return true 若合法 (若不合法通常會直接拋出例外)
	 */
	public Boolean isTokenValid(String token) {
		getSubject(token); // 若 token 有任何例外 (過期、竄改)，則由 jjwt 套件直接拋出錯誤。
		return true; // 能走到這一步表示驗證通過，token 合法
	}

}
