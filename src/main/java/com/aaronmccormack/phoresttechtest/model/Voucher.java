package com.aaronmccormack.phoresttechtest.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Voucher {
	private String clientId;
	private String branchId;
	private String expiryDate;
	private String issueDate;
	private Double originalBalance;

	public Voucher(String clientId, String branchId, Double originalBalance) {
		this.clientId = clientId;
		this.branchId = branchId;
		this.expiryDate = DateTimeFormatter.ISO_DATE.format(LocalDate.now().plusYears(1));
		this.issueDate = DateTimeFormatter.ISO_DATE.format(LocalDate.now());
		this.originalBalance = originalBalance;
	}
}
