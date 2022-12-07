package site.metacoding.firstapp.domain.love;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.firstapp.web.dto.response.love.PostRespDto;

public interface LoveDao {

	public Love findById(Integer loveId);

	public List<Love> findAll();

	public void insert(Love love);

	public void update(Love love);

	public void delete(Integer loveId);

	public Integer findByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);

	public List<PostRespDto> findLoveList(Integer userId);

}
