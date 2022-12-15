package site.metacoding.firstapp.web.dto.response.user;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
	// 유저 프로필 정보
	private Integer userId;
	private String username;
	private String nickname;
	private String email;
	private String profileImg;
	private Timestamp createdAt;

	// DB 컬럼 아님
	private boolean isSubscribe;
	private boolean isMy;

	// 내가 방문한 게시글목록
	List<MyVisitListDto> myVisitListDto;

	// 내가 작성한 게시글목록
	List<MyPostListDto> myPostListDto;

}
