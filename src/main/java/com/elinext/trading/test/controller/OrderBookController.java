package com.elinext.trading.test.controller;

import com.elinext.trading.test.entity.OrderBookView;
import com.elinext.trading.test.entity.OrderSide;
import com.elinext.trading.test.repository.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-book")
public class OrderBookController {

	private final OrderBookRepository orderBookRepository;

	@Autowired
	public OrderBookController(OrderBookRepository orderBookRepository) {

		this.orderBookRepository = orderBookRepository;
	}

	@GetMapping("/sells")
	public List<OrderBookView> getSells() {

		return orderBookRepository.findAllBySide(OrderSide.SELL);
	}

	@GetMapping("/purchases")
	public List<OrderBookView> getPurchases() {

		return orderBookRepository.findAllBySide(OrderSide.BUY);
	}

}
