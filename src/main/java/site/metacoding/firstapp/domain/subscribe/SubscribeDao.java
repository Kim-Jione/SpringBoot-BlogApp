package site.metacoding.firstapp.domain.subscribe;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SubscribeDao {
	public Subscribe findById(Integer subscribeId);

	public List<Subscribe> findAll();

	public void insert(Subscribe subscribe);

	public void update(Subscribe subscribe);

	public void delete(Integer subscribeId);

	public Integer findByFromUserIdAndToUserId(@Param("fromUserId") Integer fromUserId,
			@Param("toUserId") Integer toUserId);

}
