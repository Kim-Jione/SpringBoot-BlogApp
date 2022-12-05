package site.metacoding.firstapp.domain.love;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Love {
	private Integer loveId;
	private Integer usersId;
	private Integer postsId;
	private Timestamp updatedAt;
	private Timestamp createdAt;
}
