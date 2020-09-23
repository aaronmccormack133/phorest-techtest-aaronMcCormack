package com.aaronmccormack.phoresttechtest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Voucher {
	public String clientId;
	public String creatingBranchId;
	public String expiryDate;
	public String issueDate;
	public Double originalBalance;

	public Voucher(String clientId, String branchId, Double originalBalance) {
		this.clientId = clientId;
		this.creatingBranchId = branchId;
		this.expiryDate = DateTimeFormatter.ISO_DATE.format(LocalDateTime.now().plusYears(1));
		this.issueDate = DateTimeFormatter.ISO_DATE.format(LocalDateTime.now());
		this.originalBalance = originalBalance;
	}
}
