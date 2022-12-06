package site.metacoding.firstapp.domain.user;

import java.util.List;

import site.metacoding.firstapp.web.dto.request.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.LoginRespDto;


public interface UserDao {

	public User findById(Integer userId);

	public List<User> findAll();

	public void insert(User user);

	public void delete(User user);

	public User findByUsername(String username);

	public LoginRespDto login(LoginReqDto loginReqDto);
}
