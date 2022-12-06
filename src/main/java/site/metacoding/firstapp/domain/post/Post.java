package site.metacoding.firstapp.domain.post;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Post {

	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private Integer userId;
	private Timestamp updatedAt;
	private Timestamp createdAt;

	public Post(Integer userId, String postTitle, String postContent, String postThumnail) {
		this.userId = userId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postThumnail = postThumnail;
	}

}
