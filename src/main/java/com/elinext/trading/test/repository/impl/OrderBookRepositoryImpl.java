package com.elinext.trading.test.repository.impl;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.Side;
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

	private static final int MAX_MAP_SIZE = 50;

	private final TreeMap<BigDecimal, OrderBook> sellsMap = new TreeMap<>();
	private final TreeMap<BigDecimal, OrderBook> purchasesMap = new TreeMap<>(Comparator.reverseOrder());

	@Override
	public List<OrderBook> findAllBySide(Side side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		return new ArrayList<>(suitableMap.values());
	}

	@Override
	public Optional<OrderBook> findByPriceAndSide(BigDecimal price, Side side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		return Optional.ofNullable(suitableMap.get(price));
	}

	@Override
	public void save(OrderBook orderBook, Side side) {

		TreeMap<BigDecimal, OrderBook> suitableMap = getSuitableMap(side);
		suitableMap.put(orderBook.getPrice(), orderBook);

		if(suitableMap.size() > MAX_MAP_SIZE) {
			suitableMap.pollLastEntry();
		}
	}

	private TreeMap<BigDecimal, OrderBook> getSuitableMap(Side side) {

		return side == Side.BUY ? purchasesMap : sellsMap;
	}

}
