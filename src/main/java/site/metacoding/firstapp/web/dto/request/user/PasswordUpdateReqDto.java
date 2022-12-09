package site.metacoding.firstapp.web.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateReqDto {
	private String password; // 기존 비밀번호
	private String passwordUpdate; // 변경할 비밀번호
	private String passwordSame; // 비밀번호 동일한지 체크
}
