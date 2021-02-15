package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WebSocketChannelType {

	OFFLINE("offline"),
	SUBSCRIBE("subscribe"),
	SUBSCRIBED("subscribed"),
	DATA("data");

	private final String value;

	WebSocketChannelType(String value) {

		this.value = value;
	}

	@JsonValue
	public String getValue() {

		return value;
	}

	public static WebSocketChannelType getByValue(String value) {

		return Arrays.stream(WebSocketChannelType.values()).filter(side -> side.value.equals(value)).findAny().orElseThrow(IllegalArgumentException::new);
	}

}
