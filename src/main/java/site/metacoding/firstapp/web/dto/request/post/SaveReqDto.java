package site.metacoding.firstapp.web.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.domain.post.Post;

@Getter
@Setter
public class SaveReqDto {
	private Integer userId;
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;

	public Post toEntity() {
		Post post = new Post(this.userId, this.postId,this.postTitle, this.postContent, this.postThumnail, this.role);
		return post;
	}
}
