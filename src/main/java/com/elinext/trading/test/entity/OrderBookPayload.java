package com.elinext.trading.test.entity;

import java.math.BigDecimal;

public class OrderBookPayload {

	private BigDecimal price;

	private OrderSide side;

	private BigDecimal size;

	private String market;

	public BigDecimal getPrice() {

		return price;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public OrderSide getSide() {

		return side;
	}

	public void setSide(OrderSide side) {

		this.side = side;
	}

	public BigDecimal getSize() {

		return size;
	}

	public void setSize(BigDecimal size) {

		this.size = size;
	}

	public String getMarket() {

		return market;
	}

	public void setMarket(String market) {

		this.market = market;
	}

	@Override
	public String toString() {

		return "OrderBook{" + "price=" + price + ", side=" + side + ", size=" + size + ", market='" + market + '\'' + '}';
	}
}
