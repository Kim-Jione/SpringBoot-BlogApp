package site.metacoding.firstapp.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.firstapp.domain.user.User;

@NoArgsConstructor
@Getter
@Setter
public class JoinReqDto {
	private String username;
	private String nickname;
	private String password;
	private String email;

	public User toUser() {
		return User.builder().username(this.username).password(password).nickname(nickname).email(email).build();
	}
}