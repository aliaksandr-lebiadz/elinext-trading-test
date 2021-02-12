package com.elinext.trading.test.handler;

import com.elinext.trading.test.entity.OrderBook;
import com.elinext.trading.test.entity.OrderSide;
import com.elinext.trading.test.entity.ChannelPayload;
import com.elinext.trading.test.repository.OrderBookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebSocketHandlerImpl extends AbstractWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandlerImpl.class);

	private static final String CHANNEL_NAME = "orderbook";
	private static final String SUB_CHANNEL_NAME = "btc-eur";
	private static final String PURCHASES_DESTINATION = "/order-book/purchases";
	private static final String SELLS_DESTINATION = "/order-book/sells";

	private final SimpMessagingTemplate template;
	private final ObjectMapper objectMapper;
	private final OrderBookRepository orderBookRepository;

	@Autowired
	public WebSocketHandlerImpl(SimpMessagingTemplate template, ObjectMapper objectMapper, OrderBookRepository orderBookRepository) {

		this.template = template;
		this.objectMapper = objectMapper;
		this.orderBookRepository = orderBookRepository;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String messageContent = new String(message.asBytes());
		logger.info("Text message received: {}", messageContent);

		ChannelPayload payload = objectMapper.readValue(messageContent, ChannelPayload.class);
		if (payload.getType().equals("data")) {
			var orderBooks = payload.getData();
			var suitableOrderBooks = orderBooks.stream().filter(orderBook -> !orderBook.getSize().equals(BigDecimal.ZERO)).collect(Collectors.toList());
			suitableOrderBooks.forEach(orderBookRepository::add);

			sendOrderBooks(suitableOrderBooks, OrderSide.BUY, PURCHASES_DESTINATION);
			sendOrderBooks(suitableOrderBooks, OrderSide.SELL, SELLS_DESTINATION);
		}

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		logger.info("Socket connection established!");

		ChannelPayload subscriptionPayload = new ChannelPayload(CHANNEL_NAME, SUB_CHANNEL_NAME, "subscribe");
		String subscriptionPayloadText = objectMapper.writeValueAsString(subscriptionPayload);

		session.sendMessage(new TextMessage(subscriptionPayloadText));
	}

	private void sendOrderBooks(List<OrderBook> orderBooks, OrderSide side, String destination) {

		boolean orderBookWithSideExists = orderBooks.stream().anyMatch(orderBook -> orderBook.getSide() == side);
		if (orderBookWithSideExists) {
			template.convertAndSend(destination, orderBookRepository.findAllBySide(side));
		}
	}

}
