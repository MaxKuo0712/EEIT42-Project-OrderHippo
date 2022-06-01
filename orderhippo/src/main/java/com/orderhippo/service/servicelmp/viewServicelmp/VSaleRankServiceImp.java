package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VSaleRankBean;
import com.orderhippo.repository.viewRepository.VSaleRankRepository;
import com.orderhippo.service.service.viewService.VSaleRankService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VSaleRankServiceImp implements VSaleRankService {
	
	@Autowired
	private VSaleRankRepository vSaleRankRepository;
	
	// 查詢所有 SaleRank
	public List<VSaleRankBean> getAllSaleRank() {
		List<VSaleRankBean> result = vSaleRankRepository.findAll();
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
