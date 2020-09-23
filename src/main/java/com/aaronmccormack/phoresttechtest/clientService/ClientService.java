package com.aaronmccormack.phoresttechtest.clientService;

import com.aaronmccormack.phoresttechtest.model.Client;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class ClientService {

	public RestOperations restTemplate;

	public ClientService(RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder
				.additionalMessageConverters(Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON))
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.basicAuthentication(System.getenv("phorest-username"), System.getenv("phorest-password"))
				.build();
	}
//	public String getAllClients(String urlEndpoint, String email, String phone){
//		return restTemplate.getForObject(urlEndpoint, String.class, email, phone);
//	}
}
