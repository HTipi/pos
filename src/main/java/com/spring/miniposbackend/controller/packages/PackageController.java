package com.spring.miniposbackend.controller.packages;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.packages.PackageItemView;
import com.spring.miniposbackend.service.admin.ItemBranchService;


@RestController
@RequestMapping("package")
public class PackageController {


	@Autowired
	private ItemBranchService itemBranchService;
	
	@PutMapping("updateitempackage/{itemPackageId}")
	public SuccessResponse update(@PathVariable long itemPackageId,@RequestBody List<PackageItemView> packageItem) throws Exception {
		return new SuccessResponse("00","update successful",itemBranchService.updateItemPackage(itemPackageId,packageItem));
	}
	

	
//	@GetMapping("showbypackage/{packageId}")
//	public SuccessResponse showByPackage(@RequestParam int packageId) {
//		return new SuccessResponse("00","update successful",packageitemservice.showByPackage(packageId));
//	}
//	

	
//	@PutMapping("updatepackage/{packageId}")
//	public SuccessResponse update(@PathVariable int packageId,@RequestBody PackageView packageView) {
//		return new SuccessResponse("00","update successful",packageitemservice.updatePackage(packageId,packageView));
//	}
	
//	@GetMapping("showJsonb/{itemBranchId}")
//	public SuccessResponse show(@PathVariable Long itemBranchId) {
//		return new SuccessResponse("00","Get itemBranchPackage successful",itemBranchService.getPackge(itemBranchId));
//	}
	
	@PostMapping("jsonb/{itembranchId}") 
//	@PreAuthorize("hasAnyRole('OWNER')") 
	public SuccessResponse createjsonb(@PathVariable Long itembranchId,@RequestBody List<PackageItemView> packageItem) throws Exception {
		return new SuccessResponse("00", "Create Item", itemBranchService.createjsonb(itembranchId,packageItem));
	}
	
}
