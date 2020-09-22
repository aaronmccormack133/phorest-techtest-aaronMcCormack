package com.aaronmccormack.phoresttechtest.controller;

import com.aaronmccormack.phoresttechtest.clientService.ClientService;
import com.aaronmccormack.phoresttechtest.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

	// the API endpoint
	private final String urlEndpoint = "http://api-gateway-dev.phorest.com/third-party-api-server/api/business/" + System.getenv("phorest-businessId");

	private final RestOperations restTemplate;
	private Client client;

	// Basic authentication to add to all of the requests sent to the API
	public ClientController(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}

	@GetMapping
	public ModelAndView getClients(@RequestParam(required = false) String email, @RequestParam(required = false) String mobile){
		// TODO: Handle for multiple users
		// construct for Voucher
		// construct for UI
		List<Client> clientList = new ArrayList<>();
		ModelAndView mav = new ModelAndView();

		// if the email string is not empty, construct that query
		if(email != null && !email.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("email", email)
					.build()
					.encode() // Request to have the URI template pre-encoded at build time, and URI variables encoded separately when expanded.
					.toUri();

			clientList = restTemplate.getForObject(targetUrl, Client.class);
		}
		// if the email string is empty and the mobile is not, construct that query
		else if(mobile != null && !mobile.isEmpty()){
			URI targetUrl = UriComponentsBuilder.fromUriString(urlEndpoint)
					.path("/client/")
					.queryParam("phone", mobile)
					.build()
					.encode()
					.toUri();

			var queryResponse = restTemplate.getForObject(targetUrl, Client.class);
		}

		mav.addObject()
		return mav;
	}

	@GetMapping("/client")
	public ModelAndView client(){
		List<Client> clientList = new ArrayList<>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("client", clientList);
		mav.setViewName("index");
		return mav;
	}
}
