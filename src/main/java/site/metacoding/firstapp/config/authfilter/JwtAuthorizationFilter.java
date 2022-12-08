package site.metacoding.firstapp.config.authfilter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.web.dto.auth.FindByUsernameDto;

@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

    private final UserDao userDao;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String header = req.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("디버그 header 비정상 : " + header);
            chain.doFilter(req, resp);
            return;
        }
        System.out.println("디버그 header : " + header);

        String token = req.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        System.out.println("디버그 token : " + token);

        // 토큰 검증
        String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();
        System.out.println("디버그 username : " + username);

        if (username != null) {
            Optional<FindByUsernameDto> user = userDao.findAllUsername(username);

           

            // 강제로 시큐리티의 세션에 접근하여 값 저장
        }
    }
}