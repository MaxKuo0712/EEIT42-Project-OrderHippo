package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VSaleCategoryBean;
import com.orderhippo.repository.viewRepository.VSaleCategoryRepository;
import com.orderhippo.service.service.viewService.VSaleCategoryService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VSaleCategoryServiceImp implements VSaleCategoryService{
	
	@Autowired
	private VSaleCategoryRepository vSaleCategoryRepository;
	
	// 查詢所有 SaleCategory
	public List<VSaleCategoryBean> getAllSaleCategory() {
		List<VSaleCategoryBean> result = vSaleCategoryRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
