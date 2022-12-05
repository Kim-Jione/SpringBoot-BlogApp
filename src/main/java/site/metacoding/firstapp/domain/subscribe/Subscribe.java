package site.metacoding.firstapp.domain.subscribe;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Subscribe {
	private Integer subscribeId;
	private Integer userId;
	private Integer usersId;
	private Timestamp updatedAt;
	private Timestamp createdAt;
}
