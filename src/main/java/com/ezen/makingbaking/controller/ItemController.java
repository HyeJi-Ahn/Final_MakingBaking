package com.ezen.makingbaking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.makingbaking.common.CamelHashMap;
import com.ezen.makingbaking.dto.ItemDTO;
import com.ezen.makingbaking.dto.ResponseDTO;
import com.ezen.makingbaking.entity.Item;
import com.ezen.makingbaking.service.item.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/list")
	//ItemDTO 받기 item cate가 null이 
	public ModelAndView itemListView(@PageableDefault(page = 0, size = 8) Pageable pageable) {
		Page<CamelHashMap> itemList = itemService.getItemList(pageable);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("list/list.html");
		mv.addObject("itemList", itemList);
		return mv;
	}
	
	@GetMapping("/item")
	public ModelAndView dayclassVeiw() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("item/getItem.html");
		return mv;
	}
	
	@GetMapping("/stock")
	public int getItemStock(@RequestParam("itemNo") int itemNo) {
		return itemService.getItemStock(itemNo);
	}
	
	@PostMapping("/list")
	public ResponseEntity<?> getPageItemList(@PageableDefault(page=0, size=4) Pageable pageable){
		ResponseDTO<ItemDTO> response = new ResponseDTO<>();
		System.out.println(pageable.getPageNumber());
		try {
			Page<Item> pageItemList = itemService.getPageItemList(pageable);
			
			Page<ItemDTO> pageItemDTOList = pageItemList.map(pageItem -> 
															 ItemDTO.builder()
															 		.itemNo(pageItem.getItemNo())
															 		.itemName(pageItem.getItemName())
															 		.itemPrice(pageItem.getItemPrice())
															 		.build()
															);
			response.setPageItems(pageItemDTOList);
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
}