package site.metacoding.firstapp.domain.visit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Visit {
	private Integer visitId;
	private Integer userId;
	private Integer postId;

	public Visit(Integer visitId, Integer userId, Integer postId) {
		this.visitId = visitId;
		this.userId = userId;
		this.postId = postId;
	}
	
}
