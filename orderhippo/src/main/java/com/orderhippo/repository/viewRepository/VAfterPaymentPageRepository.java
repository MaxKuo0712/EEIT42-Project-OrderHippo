package com.orderhippo.repository.viewRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.viewBean.VAfterPaymentPageBean;

public interface VAfterPaymentPageRepository extends JpaRepository<VAfterPaymentPageBean, String> {
	
	List<VAfterPaymentPageBean> findByOrderid(String orderid);
	
}
