package com.spring.miniposbackend.modelview;

import java.util.List;

import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.security.ClientApplication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitViewModel {
	private List<BranchSetting> settings;
	private List<ItemBranch> items;
	private List<ItemType> itemTypes;
	private List<BranchCurrency> currencies;
	private List<ImageResponse> itemImages;
	private List<ImageResponse> itemTypeImages;
	private UserResponse userResponse;
	private ClientApplication clientApplication;

	public InitViewModel(List<BranchSetting> settings, List<ItemBranch> items, List<ItemType> itemTypes,
			List<BranchCurrency> currencies, List<ImageResponse> imageItem, List<ImageResponse> imageItemType,
			UserResponse userResponse, ClientApplication clientApplication) {
		this.settings = settings;
		this.items = items;
		this.itemTypes = itemTypes;
		this.currencies = currencies;
		this.itemImages = imageItem;
		this.itemTypeImages = imageItemType;
		this.userResponse = userResponse;
		this.clientApplication = clientApplication;
	}
}
