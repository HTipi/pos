package com.spring.miniposbackend.service.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import com.spring.miniposbackend.exception.BadRequestException;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.account.AccountType;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.customer.Person;
import com.spring.miniposbackend.modelview.account.AccountCreditPoint;
import com.spring.miniposbackend.modelview.account.AccountDefault;
import com.spring.miniposbackend.modelview.account.AccountModel;
import com.spring.miniposbackend.repository.account.AccountRepository;
import com.spring.miniposbackend.repository.account.AccountTypeRepository;
import com.spring.miniposbackend.repository.admin.BranchAdveriseRepository;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.customer.PersonRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private BranchCurrencyRepository branchCurrencyRepository;
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private BranchAdveriseRepository branchAdvertiseRepository;
	@Autowired
	private ImageUtil imageUtil;

	@Value("${file.path.image.branch}")
	private String imagePath;
	@Value("${file.path.image.profile}")
	private String imagePathProfile;
	@Value("${file.path.image.advertisement}")
	private String logoAdvertise;

	public List<Account> showsByCreditQuery(String query) {
		return accountRepository.findByAccountCreditQuery(query, userProfile.getProfile().getBranch().getId()).stream()
				.limit(20).collect(Collectors.toList());
	}

	@Transactional
	public Account create(Long personId) {
		try {
			Optional<Person> person = personRepository.findById(personId);
			List<Account> acc = accountRepository.findByPersonAccInBranch(person.get().getId(),
					userProfile.getProfile().getBranch().getId());
			if (acc.size() != 0)
				throw new BadRequestException("Account is already existed", "01");
			BranchCurrency branchcurrency = branchCurrencyRepository
					.findCurByBranchId(userProfile.getProfile().getBranch().getId());
			AccountType accountType;
			Account account = new Account();
			account.setCorporate(userProfile.getProfile().getCorporate());
			account.setBranch(userProfile.getProfile().getBranch());
			account.setCurrency(branchcurrency.getCurrency());
			account.setPerson(person.get());
			account.setBalance(BigDecimal.ZERO);
			accountType = accountTypeRepository.findByCredit();
			account.setAccountType(accountType);
			accountRepository.save(account);
			Account pointAccount = new Account();
			pointAccount.setCorporate(userProfile.getProfile().getCorporate());
			pointAccount.setBranch(userProfile.getProfile().getBranch());
			pointAccount.setCurrency(branchcurrency.getCurrency());
			pointAccount.setPerson(person.get());
			pointAccount.setBalance(BigDecimal.ZERO);
			accountType = accountTypeRepository.findByPoint();
			pointAccount.setAccountType(accountType);
			return accountRepository.save(pointAccount);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Transactional
	public List<AccountDefault> showsByQuery(String query) throws Exception {
		try {
			List<Account> account = this.accountRepository
					.findByAccountBranchQuery(query, userProfile.getProfile().getUser().getPerson().getId()).stream()
					.limit(10).collect(Collectors.toList());
			if (account.size() == 0) {
				throw new ResourceNotFoundException("អ្នកពុំមានឈ្មោះក្នុងហាងនេះឡើយ");
			}

			List<AccountDefault> list = new ArrayList<>();
			for (int i = 0; i < account.size(); i++) {
				AccountDefault acc = new AccountDefault();
				acc.setAddress(account.get(i).getBranchAdress());
				acc.setBranchId(account.get(i).getBranchId());
				acc.setBranchName(account.get(i).getBranchName());
				acc.setCorporateId(account.get(i).getCorporateId());
				acc.setCorporateName(account.get(i).getCorporateName());
				String fileLocation = imagePath + "/" + account.get(i).getBranch().getLogo();
				byte[] logo;
				try {
					logo = imageUtil.getImage(fileLocation);
				} catch (IOException e) {
					logo = null;
				}
				acc.setLogo(logo);
				list.add(acc);
			}
			return list;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
//	private void point(Long accountId) {
//		AccountType accountType = accountTypeRepository.findByPoint();
//		Account account = accountRepository.findById(accountId).get();
//		account.setBalance(BigDecimal.ZERO);		
//		account.setAccountType(accountType);		
//		accountRepository.save(account);
//		}
//	
//	private void credit(Long accountId) {
//		AccountType accountType = accountTypeRepository.findByCredit();
//		Account account = accountRepository.findById(accountId).get();
//		account.setBalance(BigDecimal.ZERO);		
//		account.setAccountType(accountType);
//		accountRepository.save(account);
//		}

	public Account acc(int branchId) {
		Account account = accountRepository.findBybranchId(branchId,
				userProfile.getProfile().getUser().getPerson().getId());
		if (account == null) {
			throw new ResourceNotFoundException("not found");
		}
		return account;
	}

	public AccountModel showbybranch(Optional<Integer> branchId) throws Exception, QueryException {
		Integer branch = branchId.isPresent() ? branchId.get() : userProfile.getProfile().getBranch().getId();
		Branch branchlogo = branchRepository.findById(branch).get();
		String fileLocation = imagePath + "/" + branchlogo.getLogo();
		byte[] logo;
		try {
			logo = imageUtil.getImage(fileLocation);
		} catch (IOException e) {
			logo = null;
		}
		List<Integer> branchAdvertise = branchAdvertiseRepository.findByBranchId(branch);
		if (branchAdvertise.isEmpty()) {
			branchAdvertise = branchAdvertiseRepository.findByBranchId(1);
		}
		AccountModel account = new AccountModel();
		account.setCredit(credit(branch));
		account.setPoint(point(branch));
		account.setAccount(acc(branch));
		account.setLogo(logo);
		return account;
	}

	public AccountCreditPoint point(int branchId) {
		AccountCreditPoint point = new AccountCreditPoint();
		Optional<Account> account = accountRepository.findByPoint(branchId,
				userProfile.getProfile().getUser().getPerson().getId());
		if (account.isPresent()) {
			point.setBalance(account.get().getBalance());
			point.setAccountId(account.get().getId());
			point.setAccountTypeId(account.get().getAccountType().getId());
			return point;
		} else {
			return null;
		}

	}

	public AccountCreditPoint credit(int branchId) {
		AccountCreditPoint credit = new AccountCreditPoint();
		Optional<Account> account = accountRepository.findByCredit(branchId,
				userProfile.getProfile().getUser().getPerson().getId());
		if (account.isPresent()) {
			credit.setBalance(account.get().getBalance());
			credit.setAccountId(account.get().getId());
			credit.setAccountTypeId(account.get().getAccountType().getId());
			return credit;
		} else {
			return null;
		}

	}

	public String remark(Long personId, String remark) {
			Person person = personRepository.findById(personId)
					.orElseThrow(() -> new ResourceNotFoundException("This phone number is invalid!", "01"));
			Account credit = accountRepository
					.findByCredit(userProfile.getProfile().getBranch().getId(), person.getId())
					.orElseThrow(() -> new ResourceNotFoundException("This person doesn't have account in your branch!",
							"02"));
			Account point = accountRepository.findByPoint(userProfile.getProfile().getBranch().getId(), person.getId())
					.orElseThrow(() -> new ResourceNotFoundException("This person doesn't have account in your branch!",
							"02"));
			credit.setRemark(remark);
			point.setRemark(remark);
			accountRepository.save(credit);
			accountRepository.save(point);
			return remark;
	}

}
