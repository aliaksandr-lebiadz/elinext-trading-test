package com.elinext.trading.test.service.order.book;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.WebSocketChannelDataPayload;
import com.elinext.trading.test.entity.Side;

import java.util.List;

public interface OrderBookService {

	List<OrderBook> getAllBySide(Side side);

	void saveOrderBook(WebSocketChannelDataPayload webSocketChannelDataPayload);

}
