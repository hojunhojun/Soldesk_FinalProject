package com.ung.recipetoyou.market.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ung.recipetoyou.market.marketBeans;
import com.ung.recipetoyou.market.marketresultbeans;

@Component
public class Navershopsearch {
	
	public String cheapsearch(String query, int startN) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String response = null;
		int displayN = 20;
		headers.add("X-Naver-Client-Id", "SYkbFibCWyVOWcwIMPS6");
		headers.add("X-Naver-Client-Secret", "woJVXFaZjv");
		String body = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" 
						+ displayN + "&start=" + startN + "&sort=sim", HttpMethod.GET,
						requestEntity, String.class);
		HttpStatusCode httpStatus = responseEntity.getStatusCode();
		int status = httpStatus.value();
		response = responseEntity.getBody();
		System.out.println("Response status: " + status);
		System.out.println(response);
		return response;
	}
	
	public String adescsearch(String query, int startN, String sortN) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String response = null;
		System.out.println(sortN);
		int displayN = 100;
		headers.add("X-Naver-Client-Id", "SYkbFibCWyVOWcwIMPS6");
		headers.add("X-Naver-Client-Secret", "woJVXFaZjv");
		String body = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> responseEntity = rest.exchange(
					"https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" 
											+ displayN + "&start=" + startN + "&sort=" 
										+ sortN, HttpMethod.GET,
					requestEntity, String.class);
			HttpStatusCode httpStatus = responseEntity.getStatusCode();
			int status = httpStatus.value();
			response = responseEntity.getBody();
			System.out.println("Response status: " + status);
			System.out.println(response);
		return response;
	}
	
	public String search(String query, int startN) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String response = null;
		int displayN = 100;
		headers.add("X-Naver-Client-Id", "SYkbFibCWyVOWcwIMPS6");
		headers.add("X-Naver-Client-Secret", "woJVXFaZjv");
		String body = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> responseEntity = rest.exchange(
					"https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" + displayN + "&start=" + startN, HttpMethod.GET,
					requestEntity, String.class);
			HttpStatusCode httpStatus = responseEntity.getStatusCode();
			int status = httpStatus.value();
			response = responseEntity.getBody();
			System.out.println("Response status: " + status);
			System.out.println(response);
		return response;
	}
	public String searchsim(String query, int startN) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String response = null;
		int displayN = 100;
		headers.add("X-Naver-Client-Id", "SYkbFibCWyVOWcwIMPS6");
		headers.add("X-Naver-Client-Secret", "woJVXFaZjv");
		String body = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> responseEntity = rest.exchange(
					"https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" + displayN + "&start=" + startN +"&sort=sim", HttpMethod.GET,
					requestEntity, String.class);
			HttpStatusCode httpStatus = responseEntity.getStatusCode();
			int status = httpStatus.value();
			response = responseEntity.getBody();
			System.out.println("Response status: " + status);
		return response;
	}
	public String searchsim2(String query, int startN) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String response = null;
		int displayN = 50;
		headers.add("X-Naver-Client-Id", "SYkbFibCWyVOWcwIMPS6");
		headers.add("X-Naver-Client-Secret", "woJVXFaZjv");
		String body = "";
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> responseEntity = rest.exchange(
					"https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" + displayN + "&start=" + startN +"&sort=sim", HttpMethod.GET,
					requestEntity, String.class);
			HttpStatusCode httpStatus = responseEntity.getStatusCode();
			int status = httpStatus.value();
			response = responseEntity.getBody();
			System.out.println("Response status: " + status);
		return response;
	}
	public List<marketBeans> fromJSONtoItems(String result) {
		JSONObject rjson = new JSONObject(result);
		JSONArray items = rjson.getJSONArray("items");
		List<marketBeans> ret = new ArrayList<>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject itemJson = items.getJSONObject(i);
			marketBeans itemDto = new marketBeans(itemJson);
			ret.add(itemDto);
		}
		return ret;
	}
	
	public List<marketresultbeans> fromJSONtoItems1(String result) {
		JSONObject rjson = new JSONObject(result);
		JSONArray items = rjson.getJSONArray("items");
		List<marketresultbeans> ret = new ArrayList<>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject itemJson = items.getJSONObject(i);
			marketresultbeans itemDto = new marketresultbeans(itemJson);
			ret.add(itemDto);
		}
		return ret;
	}
}
