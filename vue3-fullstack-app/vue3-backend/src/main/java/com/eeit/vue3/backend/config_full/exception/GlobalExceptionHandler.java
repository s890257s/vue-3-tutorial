package tw.com.eeit.shop.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	/* === 其他內部錯誤，直接回應 === */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleInternalServerException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	/* === 未登入或權限不足 === */
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<String> handleInsufficientAuthenticationException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入或無權存取此資源。");
	}

	/* === 權限不足(例如刪除別人的購物車商品) === */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> AccessDeniedException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("你無權執行此操作");
	}

	/* === 登入失敗 === */
	// 帳號或密碼錯誤 > 401
	@ExceptionHandler(IncorrectAccountOrPasswordException.class)
	public ResponseEntity<String> handleIncorrectAccountOrPasswordException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗，帳號或密碼錯誤。");
	}

	// 帳號被禁用 > 403
	@ExceptionHandler(AccountDisabledException.class)
	public ResponseEntity<String> handleIncorrectAccountDisabledException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("登入失敗，此帳號已被禁止使用。");
	}

	/* === JWT === */
	// jwt 過期 > 401
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleJwtExpiredException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("jwt token 已過期，請重新登入。");
	}

	// jwt 解析錯誤 > 401
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<String> handleClaimJwtException(Exception exception) {
		printStackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("jwt token 不合法。");
	}

	private void printStackTrace(Exception exception) {
		exception.printStackTrace(); // for Dev
	}
}
