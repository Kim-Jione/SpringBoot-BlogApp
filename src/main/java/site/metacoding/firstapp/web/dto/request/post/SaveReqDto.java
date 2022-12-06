package site.metacoding.firstapp.web.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.domain.post.Post;

@Getter
@Setter
public class SaveReqDto {
	private Integer userId;
	private String postTitle;
	private String postContent;
	private String postThumnail;

	public Post toEntity() {
		Post post = new Post(this.userId, this.postTitle, this.postContent, this.postThumnail);
		return post;
	}
}