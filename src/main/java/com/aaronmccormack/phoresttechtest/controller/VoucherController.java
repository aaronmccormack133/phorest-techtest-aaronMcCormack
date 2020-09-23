package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.model.Voucher;
import com.aaronmccormack.phoresttechtest.model.VoucherDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@Controller
public class VoucherController {

	private final RestOperations restTemplate;
	private final String urlEndpoint = "http://api-gateway-dev.phorest.com/third-party-api-server/api/business/" + System.getenv("phorest-businessId");
	HttpHeaders headers = new HttpHeaders();

	private VoucherController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@PostMapping(value = "/voucher", consumes = "application/json", produces = "application/json")
	public String postVoucher(@ModelAttribute("voucherDTO") VoucherDTO voucherDto, @RequestParam String clientId){

		Voucher voucher = new Voucher(voucherDto.getClientId(), System.getenv("branchId"), voucherDto.getOriginalBalance());

		URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
				.path(voucherDto.getClientId() + "&branchId=" + System.getenv("branchId"))
				.build()
				.encode()
				.toUri();



		HttpEntity<Voucher> httpEntity = new HttpEntity<>(voucher, headers);
		restTemplate.postForObject(targetUrl, httpEntity, Voucher.class);
		return "success";
	}

	@GetMapping("/voucher/client")
	public ModelAndView voucher(@RequestParam String clientId){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("voucher");
		mav.addObject("voucherDTO", new VoucherDTO());
		return mav;
	}

}
