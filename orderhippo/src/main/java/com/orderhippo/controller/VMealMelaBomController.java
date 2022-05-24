package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.VMealMelaBomBean;
import com.orderhippo.service.VMealMelaBomService;

import io.swagger.annotations.Api;

@Api(tags = "菜單管理主頁API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class VMealMelaBomController {
	@Autowired
	private VMealMelaBomService vMealMelaBomService;
	
	@GetMapping("/vmealmealbom")
	public List<VMealMelaBomBean> getAll() {
		return vMealMelaBomService.getAll();
	}
}
