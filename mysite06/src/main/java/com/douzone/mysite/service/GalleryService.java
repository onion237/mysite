package com.douzone.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	private final GalleryRepository galleryRepository;
	private final FileUploadService fileUploadService;
	private final String GALLERY_PATH = "gallery";
	public GalleryService(GalleryRepository galleryRepository, FileUploadService fileUploadService) {
		super();
		this.galleryRepository = galleryRepository;
		this.fileUploadService = fileUploadService;
	}

	public List<GalleryVo> getList() {
		return galleryRepository.findAll();		
	}

	public boolean upload(GalleryVo vo, MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile, GALLERY_PATH);
		vo.setUrl(url);
		return galleryRepository.insert(vo);
	}
	public boolean delete(Long no) {
		return galleryRepository.delete(no);
	}
}
