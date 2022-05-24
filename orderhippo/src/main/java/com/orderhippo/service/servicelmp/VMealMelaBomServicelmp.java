package com.orderhippo.service.servicelmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.viewBean.VMealMelaBomBean;
import com.orderhippo.repository.VMealMelaBomRepository;
import com.orderhippo.service.service.VMealMelaBomService;

@Service
public class VMealMelaBomServicelmp implements VMealMelaBomService {

	@Autowired
	private VMealMelaBomRepository vMealMelaBomRepository;
	
	@Override
	public List<VMealMelaBomBean> getAll() {
		return vMealMelaBomRepository.findAll();
	}

}
