package com.spring.miniposbackend.controller.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.person.CheckPrimaryPhone;
import com.spring.miniposbackend.modelview.person.PersonNormalUpdateView;
import com.spring.miniposbackend.modelview.person.PersonPrimaryPhoneView;
import com.spring.miniposbackend.modelview.person.PersonRequest;
import com.spring.miniposbackend.service.person.PersonService;


@RestController
@RequestMapping("person")
public class PersonController {

	@Autowired
	private PersonService personservice;
	
	
	@GetMapping("id/{id}")
	public SuccessResponse show(@RequestParam (name="id") Long id) throws Exception {
		return new SuccessResponse("00","show",personservice.showByid(id));
	}
	
	@GetMapping("by-primaryphone/{primaryphone}")
	public SuccessResponse show(@PathVariable(name="primaryphone") String primaryphone) throws Exception {
		return new SuccessResponse("code","show",personservice.showByPrimaryphone(primaryphone));
	}
	
	@PostMapping("register") 
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse register(@RequestBody PersonRequest request) throws Throwable{
		return new SuccessResponse("00","completed Register",personservice.create(request));
				}
		
//	@PostMapping("register") 
//	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
//	public SuccessResponse register(@RequestBody PersonUserView personuserview) throws Throwable{
//		return new SuccessResponse("00","completed Register",personservice.create(personuserview));
//	}
//	
	@PutMapping("update")
	public SuccessResponse update(@RequestBody PersonNormalUpdateView personview) throws Exception{
		return new SuccessResponse("00","completed updated",personservice.update(personview));
	}
	
//	@PatchMapping("updatepassword")
//	public SuccessResponse updatePassword(@RequestBody PersonPasswordView personview) throws Exception{
//		return new SuccessResponse("code","completed updated",personservice.updatePassword(personview));
//	}
//	
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable Long id){
		personservice.delete(id);
	}
	
	@PostMapping("uploadimage")
	public SuccessResponse update(@RequestParam MultipartFile file) throws Throwable{
		return new SuccessResponse("00","completed updated",personservice.uploadimage(file));
	}
	

	@PatchMapping("updatephonenumber")
	public SuccessResponse updatephonenumber(@RequestBody PersonPrimaryPhoneView PersonPrimaryphoneView) throws Exception{
		return new SuccessResponse("00","completed updated",personservice.updatePrimaryPhone(PersonPrimaryphoneView));
	}
	
	@PostMapping("checkprimaryphone")
	public Object checkprimaryphone(@RequestBody CheckPrimaryPhone primaryphone){
			return personservice.checkPrimaryphone(primaryphone.getPrimaryphone());
		
	}
}
