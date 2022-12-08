package site.metacoding.firstapp.utill;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.firstapp.handler.ApiException;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@Slf4j
public class JWTToken {

    public static class CreateJWTToken {

        static Date expire = new Date(System.currentTimeMillis() + (1000 * 60 * 60)); // 1시간 토큰값

        public static String createToken(SessionUserDto sessionUserDto) {

            HashMap<String, Object> map = new HashMap<>(); // 해쉬맵 장점 get()을 통해서 key-value로 저장되어 있는 것을 꺼내올 수 있다,
            map.put("userId", sessionUserDto.getUserId());
            map.put("username", sessionUserDto.getUsername());

            // casting exception 발생

            // map type 저장시 primitive type(또는 해당 Wrapping class)만 지원한다.
            // 커스텀 오브젝트는 저장을 지원하지 않는다. - 에러발생
            String jwtToken = JWT.create()
                    .withExpiresAt(expire) // 토큰 만료시간
                    .withClaim("sessionUserDto", map) // 로그인 데이터 작성
                    .sign(Algorithm.HMAC512(SecretKey.SECRETKEY.key())); // 어떤 알고리즘 쓸지, 512가 보안등급이 더 좋음

            return jwtToken; // 토큰 만들어서 return 해줌
        }
    }

    /*
     * 웹서버에서 웹 브라우저에 쿠키를 전송할 때 http응답메시지 형태로 보낸다.
     * 
     * 쿠키는 html과 함께 전송된다고 했다.
     * 
     * 따라서 Html <body>에서 쿠키를 전송하기보다 <head>에서 사용하자.
     */

    public static class CookieForToken {

        public static Cookie setCookie(String token) {
            Cookie cookie = new Cookie("Authorization", token); // Cookie에 Bearer 추가하면 안됨 - 최대 공간 초과....
            cookie.setMaxAge(6 * 100 * 60); // 토큰값도 1시간이니 같게 해줌
            return cookie;
        }

        public static String cookieToToken(Cookie[] cookies) {
            String token = null;

            if (cookies == null) {
                return null;
            }

            // 쿠키내의 토큰 찾기
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization"))
                    token = cookie.getValue();
            }
            return token;
        }

    }

    public static class TokenVerificationForCookie {

        // 토큰 검증 메서드
        public Boolean Verification(String token) {

            if (token == null) {
                return false;
            }

            // 토큰 검증
            token = token.replace("Authorization=", "");
            token = token.trim(); // 검증전 공백제거

            try {

                // log.debug("디버그 : 토큰확인 - " + token);

                Date now = new Date(System.currentTimeMillis());

                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecretKey.SECRETKEY.key())).build().verify(token);

                // log.debug("디버그 : 만료시간 - " + decodedJWT.getExpiresAt().toString());
                // log.debug("디버그 : 현재시간 - " + now);

                // 입력받은 토큰값이 현재시간을 넘지 않았을 경우 true를 반환 - 만료된 토큰이 아닌지 판별
                if (decodedJWT.getExpiresAt() != null && decodedJWT.getExpiresAt().after(now)) {
                    return true;
                }

            } catch (Exception e) {
                throw new ApiException("만료된 토큰 혹은 잘못된 토큰이 입력되었습니다.");
            }

            return false;

        }
    }

    public static class TokenVerificationForHeader {

        // 토큰 검증 메서드
        public Boolean Verification(String token) {

            if (token == null) {
                return false;
            }

            // 토큰 검증 - 검증전 공백제거
            token = token.replace("Bearer ", "");
            token = token.trim();

            try {

                // log.debug("디버그 : 토큰확인 - " + token);

                Date now = new Date(System.currentTimeMillis());

                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecretKey.SECRETKEY.key())).build().verify(token);

                // log.debug("디버그 : 만료시간 - " + decodedJWT.getExpiresAt().toString());
                // log.debug("디버그 : 현재시간 - " + now);

                // 입력받은 토큰값이 현재시간을 넘지 않았을 경우 true를 반환 - 만료된 토큰이 아닌지 판별
                if (decodedJWT.getExpiresAt() != null && decodedJWT.getExpiresAt().after(now)) {
                    return true;
                }

            } catch (Exception e) {
                throw new ApiException("만료된 토큰 혹은 잘못된 토큰이 입력되었습니다.");
            }

            return false;

        }
    }

    public static class TokenToSinedDto {
        Integer userId = null;
        String username = null;

        // 토큰 -> 로그인Dto 변경 로직 ..... 뭔가 더러움
        public SessionUserDto tokenToSignedDto(Map<String, Object> getSigned) {
            for (String key : getSigned.keySet()) {

                if (key.equals("userId")) {

                    userId = ((Integer) getSigned.get(key));

                }
                if (key.equals("username")) {

                    username = (getSigned.get(key).toString());

                }
                
            }

            return new SessionUserDto(userId, username);
        }

    }

}
