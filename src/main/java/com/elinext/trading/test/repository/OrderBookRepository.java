package com.elinext.trading.test.repository;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.Side;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderBookRepository {

	List<OrderBook> findAllBySide(Side side);

	Optional<OrderBook> findByPriceAndSide(BigDecimal price, Side side);

	void save(OrderBook orderBook, Side side);

}
