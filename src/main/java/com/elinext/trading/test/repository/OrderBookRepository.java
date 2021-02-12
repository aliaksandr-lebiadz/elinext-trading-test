package com.elinext.trading.test.repository;

import com.elinext.trading.test.entity.OrderBookView;
import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderSide;

import java.util.List;

public interface OrderBookRepository {

	List<OrderBookView> findAllBySide(OrderSide side);

	//TODO return
	void add(OrderBook entity);

}
