package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.model.Voucher;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Controller
public class VoucherController {

	private final RestOperations restTemplate;

	private VoucherController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@PostMapping(value = "/voucher", consumes = "application/json", produces = "application/json")
	public Voucher postVoucher(@RequestBody String amount){
		return null;
	}

}
