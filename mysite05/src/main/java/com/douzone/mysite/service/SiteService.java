package com.douzone.mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {
	private final SiteRepository siteRepository;
	private final FileUploadService fileUploadService;
	private final String SITE_PATH = "site";
	
	public SiteService(SiteRepository siteRepository, FileUploadService fileUploadService) {
		super();
		this.siteRepository = siteRepository;
		this.fileUploadService = fileUploadService;
	}
	
	public SiteVo getCurrentSite() {
		return siteRepository.findLatest();
	}
//	public boolean updateSite(SiteVo vo) {
//		return siteRepository.update(vo);
//	}

	public boolean updateSite(SiteVo vo, MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile, SITE_PATH);
		vo.setProfile(url);
		
		return siteRepository.update(vo);
	}
	
}
