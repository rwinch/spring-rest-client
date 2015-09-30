package org.springframework.reacvtive.client.mockmvcstyle;

/**
 * @author Spencer Gibb
 */
public interface RestClient {
	<T> T perform(RequestBuilder.TypedRequestBuilder<T> requestBuilder);
}
