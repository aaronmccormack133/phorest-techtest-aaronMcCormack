package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.model.Voucher;
import com.aaronmccormack.phoresttechtest.model.VoucherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Duration;
import java.util.Collections;

@EnableWebMvc
@RestController
public class VoucherController {

	private final RestOperations restTemplate;
	private final String urlEndpoint = "http://api-gateway-dev.phorest.com/third-party-api-server/api/business/" + System.getenv("phorest-businessId");


	private VoucherController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.defaultHeader("Content-Type", "application/json")
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@PostMapping(value = "/voucher", produces = "application/json")
	public void postVoucher(@ModelAttribute("voucherDTO") VoucherDTO voucherDto, @RequestParam String clientId) throws JsonProcessingException {
		// Setting the headers to send the POST request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// Structure voucher model
		Voucher voucher = new Voucher(voucherDto.getClientId(), System.getenv("phorest-branchId"), voucherDto.getOriginalBalance());

		// Check that the balance isn't less than 0
		if(voucherDto.getOriginalBalance() <= 0){
			throw new IllegalArgumentException("Must be a positive figure");
		}

		// the endpoint
		String targetUrl = urlEndpoint + "/voucher";

		// Map the Voucher model to JSON object for POST
		ObjectMapper map = new ObjectMapper();
		String json = map.writeValueAsString(voucher);

		HttpEntity httpEntity = new HttpEntity<>(json, headers);
		// Post to the urlEndpoint with body and headers, for the Voucher model
		ResponseEntity<Voucher> response = restTemplate.postForEntity(targetUrl, httpEntity, Voucher.class);

		// Check that the response code was 201 so the post was created successfully
		if(response.getStatusCode() == HttpStatus.CREATED){
			System.out.println("Post Created");
		}
		else{
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
	}

	// ClientId is here
	// Default state for the voucher
	@GetMapping("/voucher/client")
	public ModelAndView voucher(@RequestParam String clientId){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("voucher");
		mav.addObject("voucherDTO", new VoucherDTO());
		return mav;
	}

}
