package site.metacoding.firstapp.web.dto.response.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRespDto {
	private Integer userId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
}
