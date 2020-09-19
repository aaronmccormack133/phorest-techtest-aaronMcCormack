package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.clientService.ClientService;
import com.aaronmccormack.phoresttechtest.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@Controller(value = "/")
public class ClientController {

	private final String urlEndpoint = "http://api-gateway-dev.phorest.com/third-party-api-server/api/business/" + System.getenv("phorest-businessId");

	private final RestOperations restTemplate;

	// Basic authentication to add to all of the requests sent to the API
	public ClientController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@GetMapping(value = "/client")
	public String getAllClients(@RequestParam(required = false) String email, @RequestParam(required = false) String mobile){
		// email is empty do mobile and if mobile is empty do email
		if(email != null && !email.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("email", email)
					.build()
					.encode()
					.toUri();

			System.out.println("email param: " + targetUrl);
			return restTemplate.getForObject(targetUrl, String.class);
		}
		else if(mobile != null && !mobile.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("phone", mobile)
					.build()
					.encode()
					.toUri();

			System.out.println("mobile param: " + targetUrl);
			return restTemplate.getForObject(targetUrl, String.class);
		}
		return null;
	}
}
