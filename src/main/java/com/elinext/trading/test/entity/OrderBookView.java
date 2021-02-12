package com.elinext.trading.test.entity;

import java.math.BigDecimal;

public class OrderBookView {

	private BigDecimal price;
	private BigDecimal quantity;
	private BigDecimal totalPrice;

	public OrderBookView(BigDecimal price, BigDecimal quantity, BigDecimal totalPrice) {

		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public BigDecimal getQuantity() {

		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {

		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice() {

		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {

		this.totalPrice = totalPrice;
	}
}
