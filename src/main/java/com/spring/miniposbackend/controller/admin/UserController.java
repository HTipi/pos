package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.modelview.person.LoginView;
import com.spring.miniposbackend.modelview.person.PersonPasswordView;
import com.spring.miniposbackend.service.admin.UserService;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ImageUtil imageUtil;

	@Value("${file.path.image.branch}")
	private String imagePath;
	
	@Value("${file.path.image.branch-channel}")
	private String imagePathQR;
	@GetMapping("me")
	public SuccessResponse getProfile() {
		String fileLocation = imagePath + "/" 
				+ userProfile.getProfile().getBranch().getLogo();
		byte[] image;
		try {
			image = imageUtil.getImage(fileLocation);
		} catch (IOException e) {
			image = null;
		}
		String fileLocationQR = imagePathQR + "/" 
				+ userProfile.getProfile().getBranch().getQr();
		byte[] imageQR;
		try {
			imageQR = imageUtil.getImage(fileLocationQR);
		} catch (IOException e) {
			imageQR = null;
		}
		return new SuccessResponse("00", "fetch ME",
				new UserResponse(userService.showByUsername(userProfile.getProfile().getUsername()),
						userService.getRoleByUserId(userProfile.getProfile().getUser().getId()), image,imageQR));
	}

	@PostMapping("reset-password")
	public SuccessResponse resetPassword(@RequestParam("current-password") String currentPassword,
			@RequestParam("new-password") String newPassword) {
		return new SuccessResponse("00", "លេខសំងាត់ត្រូវបានប្តូរដោយជោគជ័យ",
				userService.resetPassword(userProfile.getProfile().getUsername(), currentPassword, newPassword));
	}

	@PatchMapping("reset-passwordcustomer")
	public SuccessResponse resetPasswordCustomer(@RequestBody PersonPasswordView personPasswordView) throws Exception {
		return new SuccessResponse("00", "Get code successfully",userService.resetPasswordCustomer(personPasswordView.getNewPassword()));
	} 
	
	@PatchMapping("reset-firstlogin")
	public SuccessResponse resetFirstLogin(@RequestBody PersonPasswordView personPasswordView) throws Exception {
		return new SuccessResponse("00", "Get code successfully",userService.resetPasswordCustomerFirstLogin(personPasswordView.getNewPassword()));
	}
	
	@PostMapping("verify-otpcode")
	public SuccessResponse resetPasswordCustomer(@RequestBody LoginView code) throws Exception {
		return new SuccessResponse("00", "លេខសំងាត់ត្រូវបានប្តូរដោយជោគជ័យ",userService.verifyOtp(code.getCode()));
	}
	
	@PostMapping("verify-otpfirstlogin")
	public Object resetfirstlogin(@RequestBody LoginView code) throws Exception {
		return userService.otpFirstLogin(code.getCode());
	}

//    @GetMapping
//    public List<User> shows() {
//        return this.userService.shows();
//    }
//
//    @GetMapping("active")
//    public List<User> showAllActive() {
//        return this.userService.showAllActive();
//    }
//
//    @PostMapping
//    public User create(@RequestBody User user) {
//        return this.userService.create(user);
//    }
//
//    @PutMapping
//    public User update(@RequestParam Integer userId, @RequestBody User user) {
//        return this.userService.update(userId, user);
//    }
//
//    @PatchMapping("{userId}")
//    public User updateStatus(@PathVariable Integer userId, @RequestBody Boolean status) {
//        return this.userService.updateStatus(userId, status);
//    }

}
