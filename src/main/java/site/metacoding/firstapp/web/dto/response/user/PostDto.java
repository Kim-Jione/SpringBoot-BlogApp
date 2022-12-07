package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	private Integer postId;
	private Integer loveId;
	private Integer userId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String username;
	private String nickname;
	private String profileImg;
}
