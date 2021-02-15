package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OrderSide {

	BUY("buy"),
	SELL("sell");

	private final String value;

	OrderSide(String value) {

		this.value = value;
	}

	@JsonValue
	public String getValue() {

		return value;
	}

	public static OrderSide getByValue(String value) {

		return Arrays.stream(OrderSide.values()).filter(side -> side.value.equals(value)).findAny().orElseThrow(IllegalArgumentException::new);
	}
}
