package org.springframework.reacvtive.client.mockmvcstyle;

/**
 * @author Spencer Gibb
 */
public class DefaultRestClient implements RestClient {
	@Override
	public <T> T perform(RequestBuilder.TypedRequestBuilder<T> requestBuilder) {
		return null;
	}
}
