package site.metacoding.firstapp.web.dto.response.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRespDto {
	private Integer postId;
	private Integer userId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;
	private String username;
	private String nickname;
	private String profileImg;
}