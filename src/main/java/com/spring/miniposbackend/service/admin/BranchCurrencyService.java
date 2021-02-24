package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.util.UserProfileUtil;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class BranchCurrencyService {

    @Autowired
    private BranchCurrencyRepository branchCurrencyRepository;
    @Autowired
    private UserProfileUtil userProfile;

    public List<BranchCurrency> showByBranchId(Integer branchId, boolean currencyEnable, boolean enable) {
        return branchCurrencyRepository.findByBranchId(branchId, currencyEnable, enable);
    }

    public BranchCurrency changeRate(Integer currencyId, BigDecimal rate) {
        BranchCurrency cur = branchCurrencyRepository.findById(currencyId).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        if (!Objects.equals(cur.getBranch().getId(), userProfile.getProfile().getBranch().getId())) {
            throw new UnauthorizedException("Transaction is unauthorized");
        }
        cur.setRate(rate);
        return branchCurrencyRepository.save(cur);
    }

}
