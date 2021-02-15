package com.elinext.trading.test.repository.impl;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderSide;
import com.elinext.trading.test.repository.OrderBookRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Repository
public class OrderBookRepositoryImpl implements OrderBookRepository {

	private final TreeMap<BigDecimal, OrderBook> sellsMap = new TreeMap<>();
	private final TreeMap<BigDecimal, OrderBook> purchasesMap = new TreeMap<>(Comparator.reverseOrder());

	@Override
	public List<OrderBook> findAllBySide(OrderSide side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		return new ArrayList<>(suitableMap.values());
	}

	@Override
	public Optional<OrderBook> findByPriceAndSide(BigDecimal price, OrderSide side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		return Optional.ofNullable(suitableMap.get(price));
	}

	@Override
	public void save(OrderBook orderBook, OrderSide side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		suitableMap.put(orderBook.getPrice(), orderBook);

		if(suitableMap.size() > 50) {
			suitableMap.pollLastEntry();
		}
	}

	private TreeMap<BigDecimal, OrderBook> getSuitableMap(OrderSide side) {

		return side == OrderSide.BUY ? purchasesMap : sellsMap;
	}

}
