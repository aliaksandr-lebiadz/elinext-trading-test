package com.elinext.trading.test.service.order.book.impl;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.WebSocketChannelDataPayload;
import com.elinext.trading.test.entity.Side;
import com.elinext.trading.test.repository.OrderBookRepository;
import com.elinext.trading.test.service.order.book.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderBookServiceImpl implements OrderBookService {

	private final OrderBookRepository orderBookRepository;

	@Autowired
	public OrderBookServiceImpl(OrderBookRepository orderBookRepository) {

		this.orderBookRepository = orderBookRepository;
	}

	@Override
	public List<OrderBook> getAllBySide(Side side) {

		return orderBookRepository.findAllBySide(side);
	}

	@Override
	public void saveOrderBook(WebSocketChannelDataPayload webSocketChannelDataPayload) {

		BigDecimal price = webSocketChannelDataPayload.getPrice();
		BigDecimal size = webSocketChannelDataPayload.getSize();
		Side side = webSocketChannelDataPayload.getSide();
		Optional<OrderBook> orderBookOptional = orderBookRepository.findByPriceAndSide(price, side);
		if(orderBookOptional.isPresent()) {
			OrderBook orderBook = orderBookOptional.get();
			BigDecimal newQuantity = orderBook.getQuantity().add(size);
			BigDecimal totalPrice = price.multiply(newQuantity);
			OrderBook newOrderBook = new OrderBook(price, newQuantity, totalPrice);
			orderBookRepository.save(newOrderBook, side);
		} else {
			BigDecimal totalPrice = price.multiply(size);
			OrderBook newOrderBook = new OrderBook(price, size, totalPrice);
			orderBookRepository.save(newOrderBook, side);
		}
	}
}
