package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Balance {

	@JsonProperty("asset_id")
	private String assetId;

	private BigDecimal available;

	private BigDecimal locked;

	public String getAssetId() {

		return assetId;
	}

	public void setAssetId(String assetId) {

		this.assetId = assetId;
	}

	public BigDecimal getAvailable() {

		return available;
	}

	public void setAvailable(BigDecimal available) {

		this.available = available;
	}

	public BigDecimal getLocked() {

		return locked;
	}

	public void setLocked(BigDecimal locked) {

		this.locked = locked;
	}

	@Override
	public String toString() {

		return "Balance{" + "assetId='" + assetId + '\'' + ", available=" + available + ", locked=" + locked + '}';
	}
}
