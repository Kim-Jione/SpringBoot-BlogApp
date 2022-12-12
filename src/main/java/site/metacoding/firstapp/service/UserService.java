package site.metacoding.firstapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.utill.SHA256;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.request.user.PasswordUpdateReqDto;
import site.metacoding.firstapp.web.dto.request.user.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.MailDTO;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.LeaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;
import site.metacoding.firstapp.web.dto.response.user.UpdateRespDto;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserDao userDao;
	private final JavaMailSender mailSender;
	private final SHA256 sha256;

	@Transactional
	public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
		String encPassword = sha256.encrypt(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword); // 회원가입으로 받은 비밀번호 암호화
		userDao.insert(joinReqDto.toUser());
		User userPS = userDao.findByUsername(joinReqDto.getUsername());
		JoinRespDto joinRespDto = new JoinRespDto(userPS);
		return joinRespDto;
	}

	public User 유저네임으로찾기(String username) {
		User userPS = userDao.findByUsername(username);
		return userPS;
	}

	@Transactional
	public SessionUserDto 로그인(LoginReqDto loginReqDto) {
		String encPassword = sha256.encrypt(loginReqDto.getPassword());
		User userPS = userDao.findByUsername(loginReqDto.getUsername());
		if (userPS == null) {
			return null;
		}
		if (userPS.getPassword().equals(encPassword)) {
			return new SessionUserDto(userPS);
		}
		throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
	}

	public UpdateRespDto 회원정보수정하기(UpdateReqDto updateReqDto, SessionUserDto principal, MultipartFile file)
			throws Exception {

		int pos = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(pos + 1);
		String filePath = "C:\\temp\\img\\";

		// 랜덤 키 생성
		String imgSaveName = UUID.randomUUID().toString();

		// 랜덤 키와 파일명을 합쳐 파일명 중복을 피함
		String imgName = imgSaveName + "." + extension;

		// 파일이 저장되는 폴더가 없으면 폴더를 생성
		File makeFileFolder = new File(filePath);
		if (!makeFileFolder.exists()) {
			if (!makeFileFolder.mkdir()) {
				throw new Exception("File.mkdir():Fail.");
			}
		}

		// 이미지 저장
		File dest = new File(filePath, imgName);
		try {
			Files.copy(file.getInputStream(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("사진저장 실패");
		}
		updateReqDto.setProfileImg(imgName);
		userDao.update(updateReqDto.toEntity());
		UpdateRespDto updateRespDto = userDao.updateResult(updateReqDto.getUserId());

		return updateRespDto;
	}

	public LeaveRespDto 회원탈퇴하기(Integer userId) {
		LeaveRespDto leaveRespDto = userDao.findByLeaveId(userId);
		userDao.leave(userId);
		return leaveRespDto;

	}

	public void 비밀번호수정하기(PasswordUpdateReqDto passwordUpdateReqDto, SessionUserDto principal) {
		String encPassword = sha256.encrypt(passwordUpdateReqDto.getPasswordUpdate());
		passwordUpdateReqDto.setPasswordUpdate(encPassword);
		userDao.passwordUpdate(passwordUpdateReqDto.getPasswordUpdate(), principal.getUserId());
	}

	public MailDTO 임시비밀번호만들기(String userEmail) {
		String str = 랜덤함수비밀번호();
		MailDTO dto = new MailDTO();
		dto.setAddress(userEmail); // 보낼 이메일 주소
		dto.setTitle("제이스토리 임시비밀번호 안내 이메일 입니다.");
		dto.setMessage("안녕하세요. 제이스토리 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
				+ str + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
		updatePassword(str, userEmail); // 임시비밀번호로 DB 업데이트
		return dto;
	}

	// 임시 비밀번호로 업데이트
	public void updatePassword(String str, String userEmail) {
		String passwordUpdate = str; // 임시비밀번호 가져오기
		Integer userId = userDao.findByUserEmail(userEmail); // 입력받은 이메일 있는지 select
		userDao.passwordUpdate(passwordUpdate, userId); // 유저 찾아서 비밀번호 업데이트
	}

	// 랜덤함수로 임시비밀번호 구문 만들기
	public String 랜덤함수비밀번호() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String str = "";

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}

	// 메일보내기
	public void 이메일보내기(MailDTO mailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		message.setFrom("보내는사람이메일"); // 적어야함
		// System.out.println("디버그 message : " + message);
		mailSender.send(message);
	}

}
