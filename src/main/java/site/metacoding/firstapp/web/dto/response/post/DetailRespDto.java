package site.metacoding.firstapp.web.dto.response.post;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailRespDto {
	private Integer ownerUserId;
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;
	private Timestamp createdAt;
	private String username;
	private String nickname;
	private String profileImg;

	private boolean isLove;
}