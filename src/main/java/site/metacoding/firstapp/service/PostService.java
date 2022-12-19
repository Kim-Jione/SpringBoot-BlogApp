package site.metacoding.firstapp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.post.PostDao;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.request.post.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.post.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.post.DetailRespDto;
import site.metacoding.firstapp.web.dto.response.post.PostRespDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.post.UpdateRespDto;
import site.metacoding.firstapp.web.dto.response.user.MyPostListDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostDao postDao;

	@Transactional
	public SaveRespDto 게시글등록하기(SaveReqDto saveReqDto, SessionUserDto principal, MultipartFile file) throws Exception {

		int pos = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(pos + 1);
		String filePath = "src\\main\\resources\\static\\img";

		// 랜덤 키 생성
		String imgSaveName = UUID.randomUUID().toString();

		// 랜덤 키와 파일명을 합쳐 파일명 중복을 피함
		String imgName = imgSaveName + "." + extension;

		// 파일이 저장되는 폴더가 없으면 폴더를 생성
		File makeFileFolder = new File(filePath);
		if (!makeFileFolder.exists()) {
			if (!makeFileFolder.mkdir()) {
				throw new Exception("File.mkdir():Fail.");
			}
		}

		// 이미지 저장
		File dest = new File(filePath, imgName);
		try {
			Files.copy(file.getInputStream(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("사진저장 실패");
		}

		saveReqDto.setPostThumnail("img/" + imgName);
		postDao.insert(saveReqDto.toEntity());
		SaveRespDto saveRespDto = postDao.saveResult(principal.getUserId());
		return saveRespDto;
	}

	@Transactional
	public UpdateRespDto 게시글수정하기(UpdateReqDto updateReqDto, SessionUserDto principal, MultipartFile file)
			throws Exception {
		int pos = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(pos + 1);
		String filePath = "src\\main\\resources\\static\\img";
		// 랜덤 키 생성
		String imgSaveName = UUID.randomUUID().toString();

		// 랜덤 키와 파일명을 합쳐 파일명 중복을 피함
		String imgName = imgSaveName + "." + extension;

		// 파일이 저장되는 폴더가 없으면 폴더를 생성
		File makeFileFolder = new File(filePath);
		if (!makeFileFolder.exists()) {
			if (!makeFileFolder.mkdir()) {
				throw new Exception("File.mkdir():Fail.");
			}
		}

		// 이미지 저장
		File dest = new File(filePath, imgName);
		try {
			Files.copy(file.getInputStream(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("사진저장 실패");
		}

		updateReqDto.setPostThumnail("img/" + imgName);
		postDao.update(updateReqDto.toEntity());
		UpdateRespDto updateRespDto = postDao.updateResult(principal.getUserId());
		return updateRespDto;
	}

	@Transactional
	public DeleteRespDto 게시글삭제하기(Integer postId) {
		DeleteRespDto deleteRespDto = postDao.deleteResult(postId);
		postDao.delete(postId);
		return deleteRespDto;
	}

	public DetailRespDto 게시글상세보기(Integer userId, Integer postId) {
		DetailRespDto detailRespDto = postDao.findByDetail(userId, postId);
		return detailRespDto;
	}

	public List<MyPostListDto> 내가쓴게시글목록보기(Integer userId) {
		List<MyPostListDto> psotRespDto = postDao.findMyPostList(userId);
		return psotRespDto;
	}

}