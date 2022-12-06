package site.metacoding.firstapp.domain.post;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;

public interface PostDao {

	public Post findById(Integer postId);

	public List<Post> findAll();

	public void insert(Post post);

	public void update(Post post);

	public void delete(Integer postId);

	public SaveRespDto saveResult(Integer userId);
}