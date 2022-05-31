package com.orderhippo.service.servicelmp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.StoreCouponBean;
import com.orderhippo.model.StoreMessageBean;
import com.orderhippo.repository.StoreMessageRepository;
import com.orderhippo.service.service.StoreMessageService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class StoreMessageServiceImp implements StoreMessageService {
	
	@Autowired
	private StoreMessageRepository storeMessageRepository;
	
	// 查詢所有店家訊息
	@Override
	public List<StoreMessageBean> getAllmessages() {
		List<StoreMessageBean> result = storeMessageRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} 
		return null;
	}
		
	// 新增單筆店家訊息
	@Override
	public Object addStoreMessage(StoreMessageBean storeMessageBean) {
		if (storeMessageBean != null) {
			try {
//				StoreMessageBean result = storeMessageRepository.save(storeMessageBean);
//				return new ResponseEntity<StoreMessageBean>(result, HttpStatus.OK);
				storeMessageRepository.save(storeMessageBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}
		
	// 查詢單筆店家訊息 By MessageID
	@Override
	public List<StoreMessageBean> getStoreMessageByMessageId(String messageid) {
		List<StoreMessageBean> result = storeMessageRepository.findByMessageid(messageid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
		
	// 查詢單筆店家訊息 By StoreID
	@Override
	public List<StoreMessageBean> getStoreMessageByStoreId(String storeid) {
		List<StoreMessageBean> result = storeMessageRepository.findByStoreid(storeid);
		
		if(!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 修改店家訊息
	@Override
	public Object updateStoreMessage(String requestID, StoreMessageBean storeMessageBean) {
		List<StoreMessageBean> message = storeMessageRepository.findByMessageid(storeMessageBean.getMessageid());
		
		if(message.size() == 1) {
			StoreMessageBean currentStoreMessages = message.get(0);
			
			storeMessageBean.setId(currentStoreMessages.getId());
			storeMessageBean.setMessageid(currentStoreMessages.getMessageid());
			storeMessageBean.setStoreid(currentStoreMessages.getStoreid());
			storeMessageBean.setReviseid(requestID);
			storeMessageBean.setRevisetime(new Date());
			
			try {
				storeMessageRepository.save(storeMessageBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<String>("資料不存在：MessageID", HttpStatus.NOT_FOUND);
	}
	
	
	// 刪除單筆店家訊息
	public Object deleteMessage(String requestID, String messageId) {
		List<StoreMessageBean> msg = storeMessageRepository.findByMessageid(messageId);
		
		if(msg.size() == 1) {
			try {
				storeMessageRepository.deleteById(msg.get(0).getId());
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：MessageID", HttpStatus.NOT_FOUND);
		
	}
}
