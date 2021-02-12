package com.elinext.trading.test.service;

import org.springframework.web.socket.WebSocketHttpHeaders;

public interface AuthorizationService {

	WebSocketHttpHeaders createHeaders();

}
