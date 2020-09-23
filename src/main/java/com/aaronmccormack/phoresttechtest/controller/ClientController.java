package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.model.Client;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class ClientController {

	// the API endpoint
	private final String urlEndpoint = "http://api-gateway-dev.phorest.com/third-party-api-server/api/business/" + System.getenv("phorest-businessId") + "/";

	private final RestOperations restTemplate;
	private Client client;

	// Basic authentication to add to all of the requests sent to the API
	public ClientController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.additionalMessageConverters(Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON))
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@GetMapping("/client")
	public ModelAndView getClients(@RequestParam(required = false) String email, @RequestParam(required = false) String mobile) throws URISyntaxException {
		// TODO: Handle for multiple users
		// construct for Voucher
		// construct for UI
		List<Client> clientList = new ArrayList<>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");

		ResponseEntity<Client[]> clientEntity = null;
		// if the email string is not empty, construct that query
		if(email != null && !email.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("email", email)
					.build()
					.encode() // Request to have the URI template pre-encoded at build time, and URI variables encoded separately when expanded.
					.toUri();

			clientEntity = restTemplate.getForEntity(targetUrl, Client[].class);
		}
		// if the email string is empty and the mobile is not, construct that query
		else if(mobile != null && !mobile.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("phone", mobile)
					.build()
					.encode()
					.toUri();

			clientEntity = restTemplate.getForEntity(targetUrl, Client[].class);
		}

		Client[] clients = clientEntity.getBody();
		System.out.println("out: " + clients);
		mav.addObject(clients);
		return mav;
	}

	@GetMapping("/")
	public ModelAndView client(){
		List<Client> clients = new ArrayList<>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("client", clients);
		mav.setViewName("index");
		return mav;
	}
}
