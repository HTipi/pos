package com.spring.miniposbackend.controller.account;

import java.util.Optional;

import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.account.AccountRequest;
import com.spring.miniposbackend.modelview.account.RemarkView;
import com.spring.miniposbackend.service.account.AccountService;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@Value("${file.path.image.branch}")
	private String imagePath;
	
	@PostMapping("create")
	public SuccessResponse create(@RequestBody AccountRequest request){
		return new SuccessResponse("00", "Created account completed", accountService.create(request.getPersonId()));
	}
	

	@GetMapping("by-branch/{branchId}")
	public SuccessResponse showByBranch(@RequestParam Optional<Integer> branchId) throws Exception {
		return new SuccessResponse("00", "Here your're informations", accountService.showbybranch(branchId));
	}
	
	@GetMapping("branches")
	public SuccessResponse showByQuery(@RequestParam("query") String query) throws Exception, QueryException {
		return new SuccessResponse("00", "Here your're informations",accountService.showsByQuery(query));
	}
	
	@GetMapping("credit")
	public SuccessResponse showByCredit(@RequestParam("query") String query) throws Exception, QueryException {
		return new SuccessResponse("00", "Here your're informations",accountService.showsByCreditQuery(query));
	}
	
	@PostMapping("remark/{personId}")
	 public SuccessResponse remark(@PathVariable Long personId,@RequestBody RemarkView remark) throws Exception {
	  return new SuccessResponse("00", "Here your're informations",accountService.remark(personId, remark.getRemark()));
	 }
	

}
