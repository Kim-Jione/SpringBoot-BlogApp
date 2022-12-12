package site.metacoding.firstapp.domain.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import site.metacoding.firstapp.web.dto.auth.FindByUsernameDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.user.InfoRespDto;
import site.metacoding.firstapp.web.dto.response.user.LeaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;
import site.metacoding.firstapp.web.dto.response.user.UpdateRespDto;

public interface UserDao {

	public User findById(Integer userId);

	public List<User> findAll();

	public void insert(User user);

	public void update(User user);

	public void delete(User user);

	public User findByUsername(String username);

	public SessionUserDto login(LoginReqDto loginReqDto);

	public InfoRespDto findByUser(Integer userId);

	public Optional<FindByUsernameDto> findAllUsername(String username);

	public User findByUsersId(Integer usersId);

	public UpdateRespDto updateResult(Integer userId);

	public void leave(Integer userId);

	public LeaveRespDto findByLeaveId(Integer userId);

	public void passwordUpdate(@Param("passwordUpdate") String passwordUpdate, @Param("userId") Integer userId);

	public Integer findByUserEmail(String userEmail);

}
