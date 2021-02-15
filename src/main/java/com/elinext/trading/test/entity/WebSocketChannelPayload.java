package com.elinext.trading.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WebSocketChannelPayload {

	@JsonProperty("chan_name")
	private String channelName;

	@JsonProperty("subchan_name")
	private String subChannelName;

	private WebSocketChannelType type;

	private List<WebSocketChannelDataPayload> data;

	public WebSocketChannelPayload() {

	}
	public WebSocketChannelPayload(String channelName, String subChannelName, WebSocketChannelType type) {

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

	public WebSocketChannelType getType() {

		return type;
	}

	public void setType(WebSocketChannelType type) {

		this.type = type;
	}

	public List<WebSocketChannelDataPayload> getData() {

		return data;
	}

	public void setData(List<WebSocketChannelDataPayload> data) {

		this.data = data;
	}

	@Override
	public String toString() {

		return "SocketPayload{" + "channelName='" + channelName + '\'' + ", subChannelName='" + subChannelName + '\'' + ", type='" + type + '\'' + ", data=" + data + '}';
	}
}
