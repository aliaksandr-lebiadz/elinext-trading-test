package com.elinext.trading.test.service.order.book;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderBookPayload;
import com.elinext.trading.test.entity.OrderSide;

import java.util.List;

public interface OrderBookService {

	List<OrderBook> getAllBySide(OrderSide side);

	void saveOrderBook(OrderBookPayload orderBookPayload);

}
