package com.elinext.trading.test.controller;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.Side;
import com.elinext.trading.test.service.order.book.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-book")
public class OrderBookController {

	private final OrderBookService orderBookService;

	@Autowired
	public OrderBookController(OrderBookService orderBookService) {

		this.orderBookService = orderBookService;
	}

	@GetMapping("/{side}")
	public List<OrderBook> getOrderBooks(@PathVariable("side") String sideValue) {

		Side side = Side.getByValue(sideValue);
		return orderBookService.getAllBySide(side);
	}

}
