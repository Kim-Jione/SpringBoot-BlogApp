package site.metacoding.firstapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

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
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.LeaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;
import site.metacoding.firstapp.web.dto.response.user.UpdateRespDto;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserDao userDao;
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

}
