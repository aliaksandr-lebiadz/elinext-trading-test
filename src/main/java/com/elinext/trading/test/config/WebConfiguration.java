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
public class WebConfiguration implements WebSocketMessageBrokerConfigurer {

	private static final String SOCKET_ENTRY_POINT = "/init";

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(SOCKET_ENTRY_POINT).withSockJS();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}