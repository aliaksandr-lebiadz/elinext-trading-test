package com.elinext.trading.test;

import com.elinext.trading.test.entity.Balance;
import com.elinext.trading.test.handler.WebSocketHandlerImpl;
import com.elinext.trading.test.service.authorization.AuthorizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootApplication
public class ApplicationRunner extends SpringBootServletInitializer implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

	private static final String ORDER_BOOK_SOCKET_URL = "wss://api-public.prelive.cex.tribe.sh/api/v1/ws";
	private static final String BALANCE_URL = "https://api-public.prelive.cex.tribe.sh/api/v1/balance";

	@Autowired
	private WebSocketHandlerImpl handler;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {

		SpringApplication.run(ApplicationRunner.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(ApplicationRunner.class);
	}

	@Override
	public void run(String... args) {

		WebSocketHttpHeaders headers = authorizationService.createHeaders();
		try {
			connectToOrderBooksSocket(headers);
			consumeBalances(headers);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void connectToOrderBooksSocket(WebSocketHttpHeaders headers) throws URISyntaxException {

		WebSocketClient client = new StandardWebSocketClient();
		client.doHandshake(handler, headers, new URI(ORDER_BOOK_SOCKET_URL));
	}

	private void consumeBalances(HttpHeaders headers) throws JsonProcessingException {

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(BALANCE_URL, HttpMethod.GET, requestEntity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			String body = response.getBody();
			List<Balance> balances = objectMapper.readValue(body, new TypeReference<List<Balance>>() {});
			logger.info("balances: {}", balances);
		} else {
			logger.error("Error during consuming balances occurred! Status code: {}", response.getStatusCode());
		}
	}
}
