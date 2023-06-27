package com.spring.miniposbackend.service.transaction;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.transaction.TransactionType;
import com.spring.miniposbackend.repository.transaction.TransactionTypeRepository;

@Service
public class TransactionTypeService {

	@Autowired
	private TransactionTypeRepository transactinoRepository;

//	public TransactionType create(TransactionTypeView transactionview) throws Exception {
//		try {
//		TransactionType transactiontype = new TransactionType();
//
//		transactiontype.setName(transactionview.getName());
//		transactiontype.setNameKh(transactionview.getNameKh());
//		transactiontype.setCurrentBalance(transactionview.getCurrentBalance());
//		transactiontype.setPreviousBalance(transactionview.getPreviousBalance());
//		BigDecimal amount = (transactionview.getPreviousBalance().add(transactionview.getCurrentBalance()));
//		transactiontype.setTransactionAmount(amount);
//		transactiontype.setColor(transactionview.getColor());
//		transactiontype.setOperater(transactionview.getOperater());
//		transactiontype.setRemark(transactionview.getRemark());
//		return transactinoRepository.save(transactiontype);
//		}catch(Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
	public List<TransactionType> getTransactionTypes() {
		return transactinoRepository.findAll();
	}

	public TransactionType getTransactionType(int transactionTypeId) {
		return transactinoRepository.findById(transactionTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("TrasactionType does not exit"));
	}
}
