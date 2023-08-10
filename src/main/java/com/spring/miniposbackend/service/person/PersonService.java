package com.spring.miniposbackend.service.person;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.admin.Sex;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.customer.Person;
import com.spring.miniposbackend.modelview.account.ShowProfileView;
import com.spring.miniposbackend.modelview.person.PersonDetailView;
import com.spring.miniposbackend.modelview.person.PersonNormalUpdateView;
import com.spring.miniposbackend.modelview.person.PersonPrimaryPhoneView;
import com.spring.miniposbackend.modelview.person.PersonRequest;
import com.spring.miniposbackend.repository.account.AccountRepository;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.repository.admin.SexRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.customer.PersonRepository;
import com.spring.miniposbackend.service.account.AccountService;
import com.spring.miniposbackend.service.admin.UserRoleService;
import com.spring.miniposbackend.service.admin.UserService;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private SexRepository sexrepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BranchCurrencyRepository branchCurrencyRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private AccountRepository accountRepository;
	@Value("${file.path.image.profile}")
	private String imagePathProfile;

	public Person showByid(Long id) throws Exception {
		return personRepository.findById(id).orElseThrow(() -> new Exception("Person doesn't exit"));
	}

	public Person showByPrimaryphone(String primaryphone) {
		return personRepository.findByPrimaryphone(primaryphone);
	}

	@Transactional
	public Person create(PersonRequest request) throws Exception {
		try {
			Optional<Person> pers = personRepository.findByPrimaryphones(request.getUserName());
			if (pers.isPresent())
				throw new BadRequestException(
						"this person that use this phone number already exit can create account only");
			Person person = new Person();
			person.setName("");
			person.setNameKh("");
			person.setFirstName("");
			person.setPrimaryPhone(request.getUserName());
			Sex sex = sexrepository.findById(1)
					.orElseThrow(() -> new BadRequestException("There only 2 sex Male and Female"));
			person.setSex(sex);
			personRepository.save(person);
			entityManager.flush();
			entityManager.clear();
			User user = new User();
			user.setApiToken(null);
			user.setBranch(userProfile.getProfile().getBranch());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode("123456"));
			user.setEnable(true);
			user.setTimeCount(0);
			user.setResetDate(19910101);
			user.setFirstLogin(true);
			user.setEnable(true);
			user.setLock(false);
			user.setTelephone(request.getUserName());
			user.setUsername(request.getUserName());
			user.setFullName("");
			user.setOtp(true);
			user.setPerson(person);
			userRepository.save(user);
			entityManager.flush();
			entityManager.clear();
			userRoleService.createCustomer(user.getId());
			accountService.create(person.getId());
			return person;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public PersonNormalUpdateView update(PersonNormalUpdateView personview) throws Exception {
		User user = userProfile.getProfile().getUser();
		Person person = user.getPerson();
		Sex sex = sexrepository.findById(personview.getSexId())
				.orElseThrow(() -> new ResourceNotFoundException("There is only male and female"));
		person.setFirstName(personview.getFirstName());
		person.setName(personview.getLastName());
		person.setNameKh(personview.getNameKh());
		person.setDateOfBirth(personview.getDateOfBirth());
		person.setSex(sex);
		personRepository.save(person);
		user.setFullName(person.getFirstName().concat(" " + person.getName()));
		userRepository.save(user);
		PersonNormalUpdateView personNormalUpdateView = new PersonNormalUpdateView();
		personNormalUpdateView.setFirstName(personview.getFirstName());
		personNormalUpdateView.setLastName(personview.getLastName());
		personNormalUpdateView.setNameKh(personview.getNameKh());
		personNormalUpdateView.setDateOfBirth(personview.getDateOfBirth());
		personNormalUpdateView.setSexId(sex.getId());
		return personNormalUpdateView;
	}

	public void delete(Long id) {
		personRepository.deleteById(id);
	}

	public PersonDetailView uploadimage(MultipartFile file) throws Throwable {
		try {
			Person person = userProfile.getProfile().getUser().getPerson();
			String baseLocation = imagePathProfile;
			String fileName = imageUtil.uploadImage(baseLocation, person.getId().toString(), file);
			person.setImage(fileName);

			personRepository.save(person);

			String fileLocationProfile = imagePathProfile + "/" + person.getImage();
			byte[] profile;
			try {
				profile = imageUtil.getImage(fileLocationProfile);
			} catch (IOException e) {
				profile = null;
			}
			PersonDetailView persondetailview = new PersonDetailView();
			persondetailview.setPerson(person);
			persondetailview.setImage(profile);
			return persondetailview;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Person updatePrimaryPhone(PersonPrimaryPhoneView personPrimaryphoneView) throws Exception {
		try {
			Person person = personRepository.findById(userProfile.getProfile().getUser().getPerson().getId())
					.orElseThrow(() -> new ResourceNotFoundException("This person doesn't exit!"));
			System.out.println(person.getPrimaryPhone());
			List<Person> allprimaryphoneindb = personRepository.findAll();
			for (int i = 0; i < allprimaryphoneindb.size(); i++) {
				if (allprimaryphoneindb.get(i).getPrimaryPhone()
						.equalsIgnoreCase(personPrimaryphoneView.getNewprimaryPhone())) {
					throw new BadRequestException("This phone number already exit");
				}
			}
			List<Account> account = accountRepository.findByPersonAccount(person.getId());
			if (account.size() == 0) {
				throw new ResourceNotFoundException("This account doesn't exit");
			}

			for (int j = 0; j < account.size(); j++) {
				if (BigDecimal.ZERO.compareTo(account.get(j).getBalance()) > 0) {
					throw new Exception("You cannot change your phone number at this time");
				}
			}
			User user = userRepository.findByprimaryphone(person.getPrimaryPhone());
			if (user == null)
				new ResourceNotFoundException("user not found");
			person.setPrimaryPhone(personPrimaryphoneView.getNewprimaryPhone());
			user.setUsername(personPrimaryphoneView.getNewprimaryPhone());
			userRepository.save(user);
			return personRepository.save(person);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public Object checkPrimaryphone(String primaryphone) {
		Optional<Person> person = personRepository.findByOnlyPrimaryphone(primaryphone);
		boolean primaryphones = accountRepository.existsByPhone(primaryphone,
				userProfile.getProfile().getBranch().getId());
		if (person.isPresent()) {
			if (primaryphones) {
				  Account credit = accountRepository.findByCredit(userProfile.getProfile().getBranch().getId(),person.get().getId())
				    .orElseThrow(()-> new ResourceNotFoundException("This person doesn't have account in your branch!"));
				  Account point = accountRepository.findByPoint(userProfile.getProfile().getBranch().getId(),person.get().getId())
				    .orElseThrow(()-> new ResourceNotFoundException("This person doesn't have account in your branch!"));
				  
				  ShowProfileView showProfileView = new ShowProfileView();
				  String fileLocation = imagePathProfile + "/" + person.get().getImage();
				  byte[] logo;
				  try {
				   logo = imageUtil.getImage(fileLocation);
				  } catch (IOException e) {
				   logo = null;
				  }
				  showProfileView.setImage(logo);
				  showProfileView.setName(person.get().getNameKh());
				  showProfileView.setCredit(credit.getBalance());
				  showProfileView.setPoint(point.getBalance());
				  showProfileView.setSex(person.get().getSex().getName());
				  showProfileView.setRemark(point.getRemark());
				  showProfileView.setPersonId(person.get().getId());
				return new SuccessResponse("01", "Have account", showProfileView);
			} else {
				return new SuccessResponse("03", "No account in the branch", person.get().getId());
			}
		} else {
			return new SuccessResponse("02", "No person", "");
		}
	}

}
