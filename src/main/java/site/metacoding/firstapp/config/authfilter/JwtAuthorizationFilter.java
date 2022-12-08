package site.metacoding.firstapp.config.authfilter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.firstapp.utill.JWTToken.CookieForToken;
import site.metacoding.firstapp.utill.JWTToken.TokenToSinedDto;
import site.metacoding.firstapp.utill.SecretKey;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@Slf4j
public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String tokenForCookie = CookieForToken.cookieToToken(req.getCookies()); // 쿠키 내의 토큰 찾기
        if (tokenForCookie == null) {
            return;
        }

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecretKey.SECRETKEY.key())).build().verify(tokenForCookie); // 토큰 해독 객체 생성
        
        //map 형식으로 저장되어있는 토큰값을 map형식으로 가져온다.
        Map<String, Object> getSigned = decodedJWT.getClaim("sessionUserDto").asMap();

        TokenToSinedDto tokenToSinedDto = new TokenToSinedDto();
        SessionUserDto sessionUserDto = tokenToSinedDto.tokenToSignedDto(getSigned);
        System.out.println("디버그 username : "+ sessionUserDto.getUsername());

        HttpSession session = req.getSession();

        session.setAttribute("principal", sessionUserDto);

        chain.doFilter(req, resp);
    }

}
