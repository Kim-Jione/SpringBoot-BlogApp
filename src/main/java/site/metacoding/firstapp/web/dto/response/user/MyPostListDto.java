package site.metacoding.firstapp.web.dto.response.user;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPostListDto {
	private Integer postId;
	private Integer ownerUserId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;
	private Timestamp updatedAt;
}
