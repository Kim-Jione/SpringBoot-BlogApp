package site.metacoding.firstapp.web.dto.response.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRespDto {
	private Integer userId;
	private String postTitle;
	private String role;
	private String postContent;
	private String postThumnail;
}
