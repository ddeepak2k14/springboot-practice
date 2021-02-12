package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.bean.UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestCallService {
	@Autowired
	RestTemplate restTemplate;
	private String userIdUrl = "localhost:8080/users/{id}";
	private String userUrl = "localhost:8080/users";

	public void callGetApiWithParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		/*
		 * UserDetails user = restTemplate.getForObject(userIdUrl, UserDetails.class);
		 */
		ResponseEntity<UserDetails> user = restTemplate.getForEntity(userIdUrl, UserDetails.class, params);
		System.out.println(user.getBody());

	}

	public void callGetApiWithHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + "accessToken");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
	}

	public void callPostApiWithRequestBody() {
		UserDetails user = new UserDetails("Deepak", "1", "test@email.com");
		UserDetails result = restTemplate.postForObject(userUrl, user, UserDetails.class);
		System.out.println(result);
	}

	public void callPostApiWithHeaders() throws JsonProcessingException {
		UserDetails user = new UserDetails("Deepak", "1", "test@email.com");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + "accessToken");

		HttpEntity<String> entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(user), headers);
		// UserDetails result = restTemplate.postForObject( userUrl, entity,
		// UserDetails.class);

		ResponseEntity<String> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
	}

	public void callApiWithListResponse() {
		ResponseEntity<List<UserDetails>> users = restTemplate.exchange(userUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserDetails>>() {
				});
		System.out.println(users.getBody());

	}

}
