package site.metacoding.firstapp.domain.user;

import java.util.List;
import java.util.Optional;

import site.metacoding.firstapp.web.dto.auth.FindByUsernameDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.user.InfoRespDto;
import site.metacoding.firstapp.web.dto.response.user.PostRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

public interface UserDao {

	public User findById(Integer userId);

	public List<User> findAll();

	public void insert(User user);

	public void delete(User user);

	public User findByUsername(String username);

	public SessionUserDto login(LoginReqDto loginReqDto);

	public InfoRespDto findByUser(Integer userId);

	public Optional<FindByUsernameDto> findAllUsername(String username);

	public User findByUsersId(Integer usersId);


}
