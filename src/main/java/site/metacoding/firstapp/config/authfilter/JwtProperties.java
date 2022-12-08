package site.metacoding.firstapp.config.authfilter;

public interface JwtProperties {
	String SECRET = "kim"; // 우리 서버만 알고 있는 비밀값
	// int EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 (1/1000초)
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}