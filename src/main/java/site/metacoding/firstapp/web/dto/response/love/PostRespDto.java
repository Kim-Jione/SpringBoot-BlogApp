package site.metacoding.firstapp.web.dto.response.love;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRespDto {
	private Integer ownerUserId;
	private Integer postId;
	private Integer loveId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String username;
	private String nickname;
	private String profileImg;
}
