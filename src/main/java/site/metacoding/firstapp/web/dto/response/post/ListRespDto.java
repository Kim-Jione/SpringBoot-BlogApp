package site.metacoding.firstapp.web.dto.response.post;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRespDto {
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;
	private String profileImg;
	private String nickname;
	private Timestamp createdAt;
}
