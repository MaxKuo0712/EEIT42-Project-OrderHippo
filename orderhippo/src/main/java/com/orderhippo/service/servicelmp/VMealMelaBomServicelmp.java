package com.orderhippo.service.servicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VMealMelaBomBean;
import com.orderhippo.repository.VMealMelaBomRepository;
import com.orderhippo.service.service.VMealMelaBomService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class VMealMelaBomServicelmp implements VMealMelaBomService {

	@Autowired
	private VMealMelaBomRepository vMealMelaBomRepository;
	
	@Override
	public List<VMealMelaBomBean> getAll() {
		return vMealMelaBomRepository.findAll();
	}

}
