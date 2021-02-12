package com.elinext.trading.test.repository.impl;

import com.elinext.trading.test.entity.OrderBookView;
import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderSide;
import com.elinext.trading.test.repository.OrderBookRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Repository
public class OrderBookRepositoryImpl implements OrderBookRepository {

	private final TreeMap<BigDecimal, OrderBookView> sellsMap = new TreeMap<>();
	private final TreeMap<BigDecimal, OrderBookView> purchasesMap = new TreeMap<>(Comparator.reverseOrder());

	@Override
	public List<OrderBookView> findAllBySide(OrderSide side) {

		var suitableMap = getSuitableMap(side);
		return new ArrayList<>(suitableMap.values());
	}

	@Override
	public void add(OrderBook orderBook) {

		var suitableMap = getSuitableMap(orderBook.getSide());
		BigDecimal price = orderBook.getPrice();
		BigDecimal size = orderBook.getSize();
		OrderBookView value = suitableMap.get(price);
		if(Objects.isNull(value)) {
			BigDecimal totalPrice = price.multiply(size);
			OrderBookView orderBookView = new OrderBookView(price, size, totalPrice);
			suitableMap.put(price, orderBookView);
			if(suitableMap.size() > 50) {
				suitableMap.pollLastEntry();
			}
		} else {
			BigDecimal newQuantity = value.getQuantity().add(size);
			BigDecimal totalPrice = price.multiply(newQuantity);
			OrderBookView orderBookView = new OrderBookView(price, newQuantity, totalPrice);
			suitableMap.put(price, orderBookView);
		}
	}

	private TreeMap<BigDecimal, OrderBookView> getSuitableMap(OrderSide side) {

		return side == OrderSide.BUY ? purchasesMap : sellsMap;
	}
}
