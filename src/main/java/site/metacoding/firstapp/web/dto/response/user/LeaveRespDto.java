package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRespDto {
	private Integer userId;
	private String username;
	private String email;
}
