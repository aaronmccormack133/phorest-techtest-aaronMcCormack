package com.aaronmccormack.phoresttechtest.model;

public class VoucherDTO {
	public String clientId;
	public double originalBalance;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public double getOriginalBalance() {
		return originalBalance;
	}

	public void setOriginalBalance(double originalBalance) {
		this.originalBalance = originalBalance;
	}
}
