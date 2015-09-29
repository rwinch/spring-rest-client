package org.springframework.reacvtive.client.targetstyle;

import com.netflix.hystrix.HystrixExecutable;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import rx.Observable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Spencer Gibb
 */
public class RestClientTests {

	public static final ParameterizedTypeReference<List<String>> STRING_LIST_TYPE = new ParameterizedTypeReference<List<String>>() { };

	// Synchronous examples ----------------------------

	public void getString() throws Exception {
		String response = new RestClient()
				.get("http://example.com")
				.as(String.class)
				.execute();
	}

	public void getListString() {
		List<String> strings = new RestClient()
				.get("http://example.com")
				.as(STRING_LIST_TYPE)
				.execute();
	}

	// Ribbon examples ----------------------------

	public void getResponseEntityWithRibbon() {

		ResponseEntity<String> response = new RestClient()
				.uriResolver(new RibbonUriResolver("user"))
				.get("/users/{userid}")
				.params(1)
				.header("myheader", "myvalue")
				.as(String.class)
				.exchange();

		response.getStatusCode();
	}

	public void postResposneEntityWithRibbon() {
		ResponseEntity response = new RestClient()
				.uriResolver(new RibbonUriResolver("user"))
				.post("/users")
				.body(new User("myuser", "myname"))
				.as(Void.class)
				.exchange();

		response.getStatusCode();
	}

	public void listenableFuture() {

		// Spring ListenableFuture example ----------------------------
		ListenableFuture<String> future = new RestClient()
				.get("http://example.com")
				.header("myheader", "myvalue")
				.as(new AsyncTarget<String>())
				.execute();

		future.addCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onFailure(Throwable ex) {
				ex.printStackTrace();
			}

			@Override
			public void onSuccess(String result) {
				System.out.println(result);
			}
		});
	}

	public void completableFuture() throws Exception {
		// Java CompletableFutureTarget example ----------------------------
		CompletableFuture<String> completableFuture = new RestClient()
				.get("http://example.com")
				.header("myheader", "myvalue")
				.as(new CompletableFutureTarget<String>())
				.execute();

		System.out.println(completableFuture.get(1, TimeUnit.SECONDS));
	}

	public void observable() {

		// RxJava Observable example ----------------------------
		Observable<String> observable = new RestClient()
				.get("http://example.com")
				.as(new ObservableTarget<String>())
				.execute();

		observable.map(s -> s);
	}

	public void hystrix() {

		// HystrixExecutable example ----------------------------
		HystrixExecutable<String> executable = new RestClient()
				.get("http://example.com")
				.as(new HystrixTarget<String>())
				.execute();

		executable.observe();

	}

	@Data
	class User {
		final String username;
		final String name;
	}
}
