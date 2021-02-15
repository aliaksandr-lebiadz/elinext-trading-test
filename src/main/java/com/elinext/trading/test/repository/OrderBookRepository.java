package com.elinext.trading.test.repository;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderSide;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderBookRepository {

	List<OrderBook> findAllBySide(OrderSide side);

	Optional<OrderBook> findByPriceAndSide(BigDecimal price, OrderSide side);

	void save(OrderBook orderBook, OrderSide side);

}
