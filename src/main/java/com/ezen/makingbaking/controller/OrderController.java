package com.ezen.makingbaking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.makingbaking.common.CamelHashMap;
import com.ezen.makingbaking.service.order.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order")
	public ModelAndView orderView() {
		ModelAndView mv = new ModelAndView();
		
//		List<CamelHashMap> orderList = orderService.getOrderList();
//		
//		mv.addObject("getOrderList", orderList);
		
		mv.setViewName("order/order.html");
		return mv;
	}
	
	@PostMapping("/orderComplete")
	public ModelAndView orderCompleteView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("order/orderComplete.html");
		return mv;
	}
}
