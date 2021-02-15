package com.elinext.trading.test.service.authorization.impl;

import com.elinext.trading.test.service.authorization.AuthorizationService;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHttpHeaders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z");
	private static final String UTC = "UTC";
	private static final String DATE_HEADER = "Date";
	private static final String API_KEY_HEADER = "ApiKey";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String DATE_FOR_SIGNATURE_PATTERN = "date: %s";
	private static final String AUTHORIZATION_PATTERN = "hmac username=\"%s\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\"%s\"";

	@Value("${api.key}")
	private String apiKey;

	@Value("${api.secret}")
	private String apiSecret;

	@Override
	public WebSocketHttpHeaders createHeaders() {

		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

		String date = getFormattedDate();
		String signature = getSignature(date);

		headers.add(API_KEY_HEADER, apiKey);
		headers.add(DATE_HEADER, date);
		headers.add(AUTHORIZATION_HEADER, String.format(AUTHORIZATION_PATTERN, apiKey, signature));

		return headers;
	}

	private String getFormattedDate() {

		ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of(UTC));
		return DATE_FORMAT.format(currentDateTime);
	}

	private String getSignature(String date) {

		String dateForSignature = String.format(DATE_FOR_SIGNATURE_PATTERN, date);
		HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, apiSecret);
		byte[] hmacResult = hmacUtils.hmac(dateForSignature);
		return Base64.getEncoder().encodeToString(hmacResult);
	}
}
