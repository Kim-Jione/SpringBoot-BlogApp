package site.metacoding.firstapp.config.authfilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.utill.SHA256;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.auth.FindByUsernameDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final UserDao userDao;
    private final SHA256 sha256;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Post요청이 아닌것을 거부
        if (!req.getMethod().equals("POST")) {
            customResponse("로그인시에는 post요청을 해야 합니다.", resp);
            return;
        }

        // Body 값 받기
        ObjectMapper om = new ObjectMapper();
        LoginReqDto loginReqDto = om.readValue(req.getInputStream(),
                LoginReqDto.class);

        // 유저네임 있는지 체크 : username은 일반+기업에서 유니크
        Optional<FindByUsernameDto> usernamePS = userDao.findAllUsername(loginReqDto.getUsername());
        usernamePS.orElseThrow(() -> new RuntimeException("아이디를 잘못 입력했습니다."));
        // 패스워드 체크

        // String encPassword = sha256.encrypt(loginDto.getPassword());
        String encPassword = usernamePS.get().getPassword();
        if (!usernamePS.get().getPassword().equals(encPassword)) {
            customResponse("패스워드가 틀렸습니다.", resp);
            return;
        }

        // JWT토큰 생성 1초 = 1/1000
        Date expire = new Date(System.currentTimeMillis() + (1000 * 60 * 60));

        String jwtToken = JWT.create()
                .withSubject("메타코딩") // 토큰 이름 
                .withExpiresAt(expire) // 토큰 유효시간
                .withClaim("userId", usernamePS.get().getUserId()) // 토큰에 담길 정보
                .withClaim("username", usernamePS.get().getUsername())
                .sign(Algorithm.HMAC512("6조")); // 토큰 암호화 알고리즘 서명은 6조, 서버만 알고 있어야 함

        // JWT토큰 응답
        customJwtResponse("로그인 성공", jwtToken, usernamePS.get(), resp);

    }

    private void customJwtResponse(String msg, String token, 
            FindByUsernameDto findByUsernameDto,
            HttpServletResponse resp)
            throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token);
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        CMRespDto<?> responseDto = new CMRespDto<>(1, "성공", new SessionUserDto(findByUsernameDto));
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }

    private void customResponse(String msg, HttpServletResponse resp) throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        resp.setStatus(400);
        CMRespDto<?> responseDto = new CMRespDto<>(-1, msg, null);
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }

}