package site.metacoding.firstapp.web.dto.request.user;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.domain.user.User;
@Setter
@Getter
public class UpdateReqDto {
	private Integer userId;
	private String nickname;
	private String email;
	private String profileImg;

	public User toEntity() {
		User user = new User(this.userId, this.nickname, this.email, this.profileImg);
		return user;
	}

}
