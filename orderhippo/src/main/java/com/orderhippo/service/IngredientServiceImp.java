package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.IngredientBean;
import com.orderhippo.repository.IngredientRepository;

@Service
public class IngredientServiceImp implements IngredientService {
	
	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	public List<IngredientBean> getAllIngredient() {
		List<IngredientBean> result = ingredientRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

}
