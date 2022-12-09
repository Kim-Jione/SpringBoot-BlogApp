package site.metacoding.firstapp.domain.user;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {
	private Integer userId;
	private String username;
	private String nickname;
	private String password;
	private String email;
	private String profileImg;
	private Timestamp updatedAt;
	private Timestamp createdAt;

	@Builder
	public User(String username, String nickname, String password, String email) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
	}

	public User(Integer userId, String nickname, String email, String profileImg) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.profileImg = profileImg;
	}
}
