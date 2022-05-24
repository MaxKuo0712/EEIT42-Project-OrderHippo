package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.VMealMelaBomBean;
import com.orderhippo.repository.VMealMelaBomRepository;

@Service
public class VMealMelaBomServicelmp implements VMealMelaBomService {

	@Autowired
	private VMealMelaBomRepository vMealMelaBomRepository;
	
	@Override
	public List<VMealMelaBomBean> getAll() {
		return vMealMelaBomRepository.findAll();
	}

}
