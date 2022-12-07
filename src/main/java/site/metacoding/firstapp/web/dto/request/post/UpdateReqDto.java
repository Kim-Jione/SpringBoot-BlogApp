package site.metacoding.firstapp.web.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.domain.post.Post;

@Getter
@Setter
public class UpdateReqDto {
	private Integer userId;
	private Integer postId;
	private String postTitle;
	private String role;
	private String postContent;
	private String postThumnail;

	public Post toEntity() {
		Post post = new Post(this.userId, this.postId,this.postTitle, this.postContent, this.postThumnail, this.role);
		return post;
	}
}
