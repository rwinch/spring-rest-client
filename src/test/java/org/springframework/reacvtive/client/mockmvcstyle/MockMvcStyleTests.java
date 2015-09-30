package org.springframework.reacvtive.client.mockmvcstyle;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.reacvtive.client.mockmvcstyle.RequestBuilder.TypedRequestBuilder;

import rx.Observable;

import static org.springframework.reacvtive.client.mockmvcstyle.RequestBuilders.*;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

/**
 * @author Spencer Gibb
 */
public class MockMvcStyleTests {
	DefaultRestClient defaultRestClient = new DefaultRestClient();

	public void getString() throws Exception {

		TypedRequestBuilder<String> request = get("http://example.com")
				.as(String.class);

		String result = defaultRestClient.perform(request);
	}

	public void getResponseEntity() {
		ParameterizedTypeReference<ResponseEntity<User>> type = new ParameterizedTypeReference<ResponseEntity<User>>() { };

		TypedRequestBuilder<ResponseEntity<User>> request = get("/users/{userid}", 1)
				.header("myheader", "myvalue")
				.as(type);

		ResponseEntity<User> result = defaultRestClient.perform(request);
	}

	public void postResponseEntity() {
		ParameterizedTypeReference<ResponseEntity<Void>> type = new ParameterizedTypeReference<ResponseEntity<Void>>() { };

		TypedRequestBuilder<ResponseEntity<Void>> request = post("http://example.com/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new User("username", "name"))
			.as(type); //this vs. two perform methods in RestClient

		ResponseEntity<Void> response = defaultRestClient.perform(request);
	}

	public void getObservable() {
		ParameterizedTypeReference<Observable<User>> type = new ParameterizedTypeReference<Observable<User>>() { };

		TypedRequestBuilder<Observable<User>> request = get("/users/{userid}", 1)
			.header("myheader", "myvalue")
			.as(type);

		Observable<User> observable = defaultRestClient.perform(request);
	}

	@Data
	class User {
		final String username;
		final String name;
	}
}
