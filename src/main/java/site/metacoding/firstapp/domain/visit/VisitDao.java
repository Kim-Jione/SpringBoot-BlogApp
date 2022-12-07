package site.metacoding.firstapp.domain.visit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VisitDao {

	public Visit findById(Integer visitId);

	public List<Visit> findAll();

	public void insert(Visit visit);

	public void delete(Visit visit);

	public Integer findByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);

	public void save(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
