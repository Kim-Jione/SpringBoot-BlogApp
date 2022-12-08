package site.metacoding.firstapp.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.metacoding.firstapp.web.dto.CMRespDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public CMRespDto<?> error(Exception e){
		return new CMRespDto<>(-1, e.getMessage(), null);
	}
}
