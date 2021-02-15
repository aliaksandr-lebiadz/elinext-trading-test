package com.elinext.trading.test.service.authorization;

import org.springframework.web.socket.WebSocketHttpHeaders;

public interface AuthorizationService {

	WebSocketHttpHeaders createHeaders();

}
