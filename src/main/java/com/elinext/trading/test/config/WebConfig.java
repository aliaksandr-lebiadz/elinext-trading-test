package com.elinext.trading.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebConfig implements WebSocketMessageBrokerConfigurer {

	private static final String ORDER_BOOK_DESTINATION_PREFIX = "/order-book";
	private static final String STOMP_ENDPOINT = "/init";

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(ORDER_BOOK_DESTINATION_PREFIX);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(STOMP_ENDPOINT).withSockJS();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}