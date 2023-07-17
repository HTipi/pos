package com.spring.miniposbackend.modelview;

import java.util.List;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.security.ClientApplication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitDashboardViewModel {
	private List<BranchSetting> settings;
	//private List<ItemBranch> items;
	//private List<ItemType> itemTypes;
	private List<BranchCurrency> currencies;
	//private List<ImageResponse> itemImages;
	//private List<ImageResponse> itemTypeImages;
	private UserResponse userResponse;
	//private ClientApplication clientApplication;
	//private List<Printer> printers;
	//private List<Invoice> invoices;
	private List<Branch> branches;

	public InitDashboardViewModel(List<BranchSetting> settings, 
			List<BranchCurrency> currencies,
			UserResponse userResponse,List<Branch> branches) {
		this.settings = settings;
		//this.items = items;
		//this.itemTypes = itemTypes;
		this.currencies = currencies;
		//this.itemImages = imageItem;
		//this.itemTypeImages = imageItemType;
		this.userResponse = userResponse;
		//this.clientApplication = clientApplication;
		//this.printers = printers;
		//this.invoices = invoices;
		this.branches = branches;
	}
}
