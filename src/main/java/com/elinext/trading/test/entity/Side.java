package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Side {

	BUY("buy"),
	SELL("sell");

	private final String value;

	Side(String value) {

		this.value = value;
	}

	@JsonValue
	public String getValue() {

		return value;
	}

	public static Side getByValue(String value) {

		return Arrays.stream(Side.values()).filter(side -> side.value.equals(value)).findAny().orElseThrow(IllegalArgumentException::new);
	}
}
