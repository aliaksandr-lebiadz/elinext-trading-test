package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChannelPayload {

	@JsonProperty("chan_name")
	private String channelName;

	@JsonProperty("subchan_name")
	private String subChannelName;

	private String type;

	private List<OrderBookPayload> data;

	public ChannelPayload() {

	}
	public ChannelPayload(String channelName, String subChannelName, String type) {

		this.channelName = channelName;
		this.subChannelName = subChannelName;
		this.type = type;
	}

	public String getChannelName() {

		return channelName;
	}

	public void setChannelName(String channelName) {

		this.channelName = channelName;
	}

	public String getSubChannelName() {

		return subChannelName;
	}

	public void setSubChannelName(String subChannelName) {

		this.subChannelName = subChannelName;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public List<OrderBookPayload> getData() {

		return data;
	}

	public void setData(List<OrderBookPayload> data) {

		this.data = data;
	}

	@Override
	public String toString() {

		return "SocketPayload{" + "channelName='" + channelName + '\'' + ", subChannelName='" + subChannelName + '\'' + ", type='" + type + '\'' + ", data=" + data + '}';
	}
}
