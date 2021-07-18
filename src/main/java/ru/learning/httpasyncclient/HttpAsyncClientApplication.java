package ru.learning.httpasyncclient;

import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@SpringBootApplication
public class HttpAsyncClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpAsyncClientApplication.class, args);
		DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
				.setConnectTimeout(500);
		AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);
		Request request = new RequestBuilder(HttpConstants.Methods.GET)
				.setUrl("http://www.baeldung.com")
				.build();
		ListenableFuture<Response> listenableFuture = client
				.executeRequest(request);
		listenableFuture.addListener(() -> {
			try {
				Response response = listenableFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}, Executors.newCachedThreadPool());
	}

}
