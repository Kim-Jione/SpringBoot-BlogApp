package site.metacoding.firstapp.web.dto.response.love;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRespDto {
	private Integer loveId;
	private Integer userId;
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String nickname;
	private String profileImg;
	private Timestamp updatedAt;
}
