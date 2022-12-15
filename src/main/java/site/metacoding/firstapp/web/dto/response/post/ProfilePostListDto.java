package site.metacoding.firstapp.web.dto.response.post;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilePostListDto {
	// DB칼럼 아님
	private boolean isSubscribe; // 팔로잉 여부
	private boolean isMy; // 본인확인

	// 프로필 유저정보
	private Integer userId;
	private String username;
	private String nickname;
	private String email;
	private String profileImg;

	// 게시글 목록정보
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postThumnail;
	private String role;
	private Timestamp createdAt;

}
