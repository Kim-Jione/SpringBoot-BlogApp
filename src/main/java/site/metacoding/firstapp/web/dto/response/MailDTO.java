package site.metacoding.firstapp.web.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {
	private String address;
	private String title;
	private String message;
}
