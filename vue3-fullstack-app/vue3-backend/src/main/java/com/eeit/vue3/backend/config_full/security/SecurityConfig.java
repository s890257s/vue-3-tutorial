package tw.com.eeit.shop.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CORS: 設定允許的 domain、method、header
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("*"));
		corsConfiguration.setAllowedHeaders(List.of("*"));

		// 設定開放的 URL，無須登入
		List<String> allowedURL = List.of(
				"/.well-known/**", // Chrome 開發工具開啟時會發出請求
				"/favicon.ico",
				"/test/**",
				"/api/auth/**",
				"/api/product/**",
				"/api/**");

		return http // 使用 HttpSecurity http 物件展開串聯設定
				.cors(cros -> cros.configurationSource(request -> corsConfiguration)) // 使用自訂的 corsConfiguration
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 無狀態
				.csrf(AbstractHttpConfigurer::disable) // 因無狀態，故不用考慮 CSRF(跨站請求偽造) 問題
				.authorizeHttpRequests(auth -> { // 設定權限主要位置

					// 將 allowedURL 中的所有路徑設為無須登入即可訪問
					for (String url : allowedURL) {
						auth.requestMatchers(url).permitAll();
					}

					// Admin API 只有管理員角色才可以存取。
					auth.requestMatchers("/admin/**").hasRole("ADMIN");

					// 除了開放的 api 以外，其他都要登入才能存取
					auth.anyRequest().authenticated();

				}) //
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 添加自訂過濾器
				.exceptionHandling(exceptionHandling -> { // 轉發驗證錯誤
					/**
					 * 為什麼要轉發錯誤? <br>
					 * Spring Security 的驗證在 Spring MVC 的執行流程之前進行。 </br>
					 * 當驗證失敗（如未授權訪問或權限不足）時，Spring Security 會拋出錯誤， </br>
					 * 但這些異常不會進入 MVC 層，因此無法被 GlobalExceptionHandler 捕獲。 </br>
					 *
					 * 故我們在此處使用 HandlerExceptionResolver 將異常轉發，<br>
					 * 以便統一由全局異常處理器進行處理。
					 */
					exceptionHandling.authenticationEntryPoint((req, resp, exception) -> {
						log.error("請求 %s 時發生錯誤".formatted(req.getServletPath()));
						handlerExceptionResolver.resolveException(req, resp, null, exception);
					});
				}).build();
	}

}
