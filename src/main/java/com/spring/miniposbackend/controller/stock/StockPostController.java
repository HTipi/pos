package com.spring.miniposbackend.controller.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.stock.StockPost;
import com.spring.miniposbackend.service.stock.StockPostService;

@RestController
@RequestMapping("stock-post")
public class StockPostController {

	@Autowired
	private StockPostService stockPostService;
	
	@PostMapping("{stockId}")
	public SuccessResponse create(@PathVariable Long stockId){
		return new SuccessResponse("00", "fetch Stock", stockPostService.create(stockId));
	}
}
