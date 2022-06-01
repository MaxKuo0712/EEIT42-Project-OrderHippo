package com.orderhippo.repository.viewRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.viewBean.VPaymentDetailBean;

public interface VPaymentDetailRepository extends JpaRepository<VPaymentDetailBean, String> {
	
	List<VPaymentDetailBean> findByOrderid(String orderid);
}
