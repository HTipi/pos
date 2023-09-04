package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.security.ClientApplication;
import com.spring.miniposbackend.model.transaction.TransactionType;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.modelview.InitDashboardViewModel;
import com.spring.miniposbackend.modelview.InitPointViewModel;
import com.spring.miniposbackend.modelview.InitViewModel;
import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.modelview.account.AccountModel;
import com.spring.miniposbackend.repository.admin.BranchAdveriseRepository;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.repository.admin.BranchPaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;
import com.spring.miniposbackend.repository.admin.PrinterRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;
import com.spring.miniposbackend.repository.sale.InvoiceRepository;
import com.spring.miniposbackend.repository.security.ClientApplicationRepository;
import com.spring.miniposbackend.repository.transaction.TransactionTypeRepository;
import com.spring.miniposbackend.service.account.AccountService;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.io.IOException;
import java.util.Optional;

@Service
public class InitService {

	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository settingBranchRepository;
	@Autowired
	private BranchCurrencyRepository branchCurrencyRepository;
	@Autowired
	private BranchPaymentChannelRepository branchPaymentChannelRepository;
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private ItemTypeRepository itemTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private ClientApplicationRepository clientApplicationRepository;
	@Autowired
	private PrinterRepository printerRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private TransactionTypeRepository transactionTypeRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BranchAdveriseRepository branchAdvertiseRepository;
	@Autowired
	private CorporateRepository corporateRepository;

	@Autowired
	private ImageUtil imageUtil;
	@Value("${file.path.image.item-type}")
	private String imagePathItemType;
	@Value("${file.path.image.item}")
	private String imagePathItem;
	@Value("${file.path.image.branch}")
	private String imagePathBranch;

	@Value("${file.path.image.branch-channel}")
	private String imagePathQR;
	@Value("${file.path.image.profile}")
	private String imagePathProfile;

	public InitViewModel showInitHorPao() throws Exception {

		try {
			Integer branchId = userProfile.getProfile().getBranch().getId();
			Integer corporateId = userProfile.getProfile().getCorporate().getId();
			List<BranchSetting> settings = getSettings(branchId);
			List<BranchCurrency> currencies = getCurrencies(branchId, true, true);
			System.out.println(currencies.size());
			// List<BranchPaymentChannel> branchPaymentChannels = getChannels(branchId);
			List<ItemBranch> itemBranch = getItemBranch(branchId);
			List<ItemType> itemType = getItemTypes(corporateId);
			List<ImageResponse> imageItemType = getImagesFromListItemType(itemType);
			List<ImageResponse> imageItem = getImagesFromListItem(itemBranch);
			UserResponse user = getUsers();
			ClientApplication clientApp = getClientApp("SALE");
			List<Printer> printers = getPrinters(branchId);
			List<Invoice> invoices = getInvoices(branchId);

			return new InitViewModel(settings, itemBranch, itemType, currencies, imageItem, imageItemType, user,
					clientApp, printers, invoices);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public InitDashboardViewModel showInitHorPaoDashboard() throws Exception {

		try {
			Integer branchId = userProfile.getProfile().getBranch().getId();
			Integer corporateId = userProfile.getProfile().getCorporate().getId();
			List<BranchSetting> settings = getSettings(branchId);
			List<BranchCurrency> currencies = getCurrencies(branchId, true, true);
			List<ItemBranch> itemBranch = getItemBranch(branchId);
			List<ItemType> itemType = getItemTypes(corporateId);
			UserResponse user = getUsers();
			List<Branch> branches = showByCorpoateId(corporateId);
			return new InitDashboardViewModel(settings, currencies, user,branches,itemBranch,itemType);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public InitPointViewModel showInitHorPaoPoint(Optional<Integer> branchId) throws Exception {

		try {
			Integer branch = branchId.isPresent() ? branchId.get() : userProfile.getProfile().getBranch().getId();
			Optional<Branch> branchlogo = branchRepository.findById(branch);
			String fileLocation = imagePathBranch + "/" + branchlogo.get().getLogo();
			byte[] logo;
			try {
				logo = imageUtil.getImage(fileLocation);
			} catch (IOException e) {
				logo = null;
			}

			BranchCurrency currency = branchCurrencyRepository.findCurByBranchId(branch);
			List<TransactionType> types = getTransactionTypes();
			UserResponse user = getUsersPoint();
			List<Integer> branchAdvertise = branchAdvertiseRepository.findByBranchId(branch);
			if (branchAdvertise.isEmpty()) {
				branchAdvertise = branchAdvertiseRepository.findByBranchId(1);
			}
			AccountModel account = new AccountModel();
			account.setCredit(accountService.credit(branch));
			account.setPoint(accountService.point(branch));
			account.setAccount(accountService.acc(branch));
			account.setLogo(logo);
			account.setBranchAdvertiseId(branchAdvertise);
			return new InitPointViewModel(user, account, currency, types);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	private List<BranchSetting> getSettings(Integer branchId) {
		return settingBranchRepository.findByBranchId(branchId);
	}

	private List<BranchCurrency> getCurrencies(Integer branchId, boolean currencyEnable, boolean enable) {
		return branchCurrencyRepository.findByBranchId(branchId, currencyEnable, enable);
	}

	private List<BranchPaymentChannel> getChannels(Integer branchId) {
		return branchPaymentChannelRepository.findByBranchId(branchId);
	}

	private List<ItemBranch> getItemBranch(Integer branchId) {
		return itemBranchRepository.findByBranchId(branchId, true);
	}

	private List<ItemType> getItemTypes(Integer corporateId) {
		return itemTypeRepository.findByCorporateId(corporateId, true);

	}

	private List<ImageResponse> getImagesFromListItemType(List<ItemType> list) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		list.forEach((requestImage) -> {
			ImageResponse image = getImage(requestImage);
			images.add(image);
		});
		return images;
	}

	private List<ImageResponse> getImagesFromListItem(List<ItemBranch> list) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		list.forEach((requestImage) -> {
			ImageResponse image = getImage(requestImage);
			images.add(image);
		});
		return images;
	}

	private ImageResponse getImage(ItemType itemType) {
		if (itemType.getImage() == null) {
			return new ImageResponse(itemType.getId().longValue(), null, itemType.getVersion());
		}
		try {
//			String fileLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base")) + "/"
//					+ itemType.getImage();
			String fileLocation = imagePathItemType + "/" + itemType.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemType.getId().longValue(), bArray, itemType.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemType.getId().longValue(), null, itemType.getVersion());
		}
	}

	private ImageResponse getImage(ItemBranch itemBranch) {
		if (itemBranch.getImage() == null) {
			return new ImageResponse(itemBranch.getItem_Id(), null, itemBranch.getVersion());
		}
		try {
			String fileLocation = imagePathItem + "/" + itemBranch.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemBranch.getItem_Id(), bArray, itemBranch.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemBranch.getItem_Id(), null, itemBranch.getVersion());
		}
	}

	private User showByUsername(String username) {
		Optional<User> user = userRepository.findFirstByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ResourceNotFoundException("User not found");
		}
	}

	private List<UserRole> getRoleByUserId(Integer userId) {
		return userRoleRepository.findByUserRoleIdentityUserId(userId, true);
	}

	private UserResponse getUsers() {
		String fileLocation = imagePathBranch + "/" + userProfile.getProfile().getBranch().getLogo();
		byte[] image;
		try {
			image = imageUtil.getImage(fileLocation);
		} catch (IOException e) {
			image = null;
		}
		String fileLocationQR = imagePathQR + "/" + userProfile.getProfile().getBranch().getQr();
		byte[] imageQR;
		try {
			imageQR = imageUtil.getImage(fileLocationQR);
		} catch (IOException e) {
			imageQR = null;
		}
		return new UserResponse(showByUsername(userProfile.getProfile().getUsername()),
				getRoleByUserId(userProfile.getProfile().getUser().getId()), image, imageQR);
	}

	private ClientApplication getClientApp(String name) {
		return clientApplicationRepository.findFirstByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Record does not exist", "01"));
	}

	private List<Printer> getPrinters(int branchId) {
		return printerRepository.findByBranchId(branchId);
	}

	private List<Invoice> getInvoices(int branchId) {
//		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
//			return invoiceRepository.findByBranchId(branchId);
//		}
//		return invoiceRepository.findByBranchId(userProfile.getProfile().getUser().getId());
		return invoiceRepository.findByBranchId(branchId);
	}

	private List<TransactionType> getTransactionTypes() {
		return transactionTypeRepository.findAll();
	}

	private UserResponse getUsersPoint() {
		String fileLocation = imagePathBranch + "/" + userProfile.getProfile().getBranch().getLogo();
		byte[] image;
		try {
			image = imageUtil.getImage(fileLocation);
		} catch (IOException e) {
			image = null;
		}
		String fileLocationQR = imagePathQR + "/" + userProfile.getProfile().getBranch().getQr();
		byte[] imageQR;
		try {
			imageQR = imageUtil.getImage(fileLocationQR);
		} catch (IOException e) {
			imageQR = null;
		}
		String fileLocationProfile = imagePathProfile + "/" + userProfile.getProfile().getUser().getPerson().getImage();
		byte[] imageProfile;
		try {
			imageProfile = imageUtil.getImage(fileLocationProfile);
		} catch (IOException e) {
			imageProfile = null;
		}
		User user = showByUsername(userProfile.getProfile().getUsername());
		return new UserResponse(user, getRoleByUserId(userProfile.getProfile().getUser().getId()), image, imageQR,
				user.getPerson(), imageProfile);
	}

	private List<Branch> showByCorpoateId(Integer corporateId) {

		return branchRepository.findByCorporateId(corporateId);

	}

}