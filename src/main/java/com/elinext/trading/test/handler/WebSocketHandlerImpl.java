package com.elinext.trading.test.handler;

import com.elinext.trading.test.entity.WebSocketChannelDataPayload;
import com.elinext.trading.test.entity.Side;
import com.elinext.trading.test.entity.WebSocketChannelPayload;
import com.elinext.trading.test.entity.WebSocketChannelType;
import com.elinext.trading.test.service.order.book.OrderBookService;
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
	private final OrderBookService orderBookService;

	@Autowired
	public WebSocketHandlerImpl(SimpMessagingTemplate template, ObjectMapper objectMapper, OrderBookService orderBookService) {

		this.template = template;
		this.objectMapper = objectMapper;
		this.orderBookService = orderBookService;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String messageContent = new String(message.asBytes());
		logger.info("Text message received: {}", messageContent);

		WebSocketChannelPayload payload = objectMapper.readValue(messageContent, WebSocketChannelPayload.class);
		if(payload.getType() == WebSocketChannelType.OFFLINE) {
			logger.warn("Server is unreachable!");
		} else if(payload.getType() == WebSocketChannelType.SUBSCRIBED) {
			logger.info("Channel is subscribed!");
		} else if (payload.getType() == WebSocketChannelType.DATA) {
			List<WebSocketChannelDataPayload> orderBooks = payload.getData();
			List<WebSocketChannelDataPayload> suitableOrderBooks = orderBooks.stream().filter(orderBook -> !orderBook.getSize().equals(BigDecimal.ZERO)).collect(Collectors.toList());
			suitableOrderBooks.forEach(orderBookService::saveOrderBook);

			sendOrderBooks(suitableOrderBooks, Side.BUY, PURCHASES_DESTINATION);
			sendOrderBooks(suitableOrderBooks, Side.SELL, SELLS_DESTINATION);
		}

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		logger.info("Socket connection established!");

		WebSocketChannelPayload subscriptionPayload = new WebSocketChannelPayload(CHANNEL_NAME, SUB_CHANNEL_NAME, WebSocketChannelType.SUBSCRIBE);
		String subscriptionPayloadText = objectMapper.writeValueAsString(subscriptionPayload);

		session.sendMessage(new TextMessage(subscriptionPayloadText));
	}

	private void sendOrderBooks(List<WebSocketChannelDataPayload> orderBooks, Side side, String destination) {

		boolean orderBookWithSideExists = orderBooks.stream().anyMatch(orderBook -> orderBook.getSide() == side);
		if (orderBookWithSideExists) {
			template.convertAndSend(destination, orderBookService.getAllBySide(side));
		}
	}

}
