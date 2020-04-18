package com.spring.miniposbackend.service.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.spring.miniposbackend.modelview.SaleTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.repository.sale.SaleRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleTemporaryRepository saleTemporaryRepository;

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ItemBranchRepository itemRepository;
    
    @Autowired
    private ReceiptService receiptService;

    public List<Sale> showSaleByUser(Integer userId, @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
    	
    	if (date.isPresent()) {
    		System.out.println(date);
    		return saleRepository.findByIdWithValueDate(userId, date.get());
    	}
        return saleRepository.findByUserId(userId);
        
        
    }

    public List<Sale> showSaleByBranch(Integer branchId) {
        return saleRepository.findByBranchId(branchId);
    }

    @Transactional
    public Sale create(Integer seatId, Integer branchId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        List<SaleTemporary> saleTemps = saleTemporaryRepository.findBySeatId(seatId);
        if (saleTemps.size() == 0) {
            throw new ResourceNotFoundException("Seat not found");
        }
        final Sale sale = new Sale();
        List<SaleDetail> listsales = new ArrayList<>();
        sale.setBranch(branch);
        sale.setUser(user);
        sale.setTotal(0.00);
        sale.setReceiptNumber("0");
        sale.setValueDate(new Date());
        final Sale saleResult = saleRepository.save(sale);
        saleTemps.forEach((saleTemp) -> {

            SaleDetail saleDeail = new SaleDetail();
            ItemBranch item = itemRepository.findById(saleTemp.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
            saleDeail.setItemBranch(item);
            saleDeail.setBranch(branch);
            saleDeail.setUser(user);
            saleDeail.setSale(saleResult);
            saleDeail.setValueDate(new Date());
            saleDeail.setDiscount(saleTemp.getDiscountAmount());
            saleDeail.setPrice(saleTemp.getPrice());
            saleDeail.setQuantity(saleTemp.getQuantity());
            saleDeail.setTotal(saleTemp.getTotal());

            listsales.add(saleDetailRepository.save(saleDeail));

        });
        saleTemporaryRepository.deleteBySeatId(seatId);
        double sum = 0.00;
        for (int i = 0; i < listsales.size(); i++) {
            sum += listsales.get(i).getTotal();
        }
        saleResult.setTotal(sum);
        String receiptNum = receiptService.getReceiptNumberByBranchId(branchId).toString();
        sale.setReceiptNumber(receiptNum);
        return saleRepository.save(saleResult);
    }

    @Transactional
    public Sale reverseSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        sale.setReverse(true);
        sale.setReverseDate(new Date());
        List<SaleDetail> saleDetail = saleDetailRepository.findBySaleId(saleId);
        if (saleDetail.size() == 0) {
            throw new ResourceNotFoundException("Record does not exist");
        }
        saleDetail.forEach((sales) -> {
            sales.setReverseDate(new Date());
            sales.setReverse(true);
            saleDetailRepository.save(sales);
        });
        return saleRepository.save(sale);
    }
    @Transactional(readOnly = true)
    public List<SaleTransaction> showSaleTranByUser(Integer userId, Optional<Long> saleId) {
        List<SaleTransaction> saleTransactions = new ArrayList<>();
        List<Sale> saleList = new ArrayList<>();
        if (saleId.isPresent())
        	 saleList = saleRepository.findByIdWithUserId(userId,saleId.get());
        else
         saleList = saleRepository.findByUserId(userId);

        if (saleList.size() == 0) {
            throw new ResourceNotFoundException("Record does not exist");
        }

        saleList.forEach((sale) -> {
            List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(sale.getId());
            saleDetails.forEach((saleDetail) -> {
                SaleTransaction saleTransaction = new SaleTransaction();
                saleTransaction.setId(sale.getId());
                saleTransaction.setBranchName(saleDetail.getBranch().getName());
                saleTransaction.setDiscount(saleDetail.getDiscount());
                saleTransaction.setItemName(saleDetail.getItemName());
                saleTransaction.setPrice(Double.parseDouble(saleDetail.getPrice().toString()));
                saleTransaction.setQuantity(saleDetail.getQuantity());
                saleTransaction.setReceiptNumber(sale.getReceiptNumber());
                saleTransaction.setTotal(saleDetail.getTotal());
                saleTransaction.setReverse(sale.isReverse());
                saleTransaction.setReverseDate(saleDetail.getReverseDate());
                saleTransaction.setValueDate(sale.getValueDate());
                saleTransaction.setUserName(sale.getUser().getFullName());
                saleTransaction.setItemId(saleDetail.getItemBranch().getId());
                saleTransactions.add(saleTransaction);
            });
        });

        return saleTransactions;


    }

}
