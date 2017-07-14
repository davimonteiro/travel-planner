package br.uece.beethoven.dsl;

import org.springframework.http.HttpMethod;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public class HttpActionBuilder {

    private HttpRequest httpRequest;

    public HttpActionBuilder() {
        this.httpRequest = new HttpRequest();
    }

    public static HttpActionBuilder request() {
        return new HttpActionBuilder();
    }

    public HttpActionBuilder get(String url) {
        requireNonNull(url);
        httpRequest.setUrl(url);
        httpRequest.setMethod(HttpMethod.GET);
        return this;
    }

    public HttpActionBuilder post(String url) {
        requireNonNull(url);
        httpRequest.setUrl(url);
        httpRequest.setMethod(HttpMethod.POST);
        return this;
    }

    public HttpActionBuilder put(String url) {
        requireNonNull(url);
        httpRequest.setUrl(url);
        httpRequest.setMethod(HttpMethod.PUT);
        return this;
    }

    public HttpActionBuilder delete(String url) {
        requireNonNull(url);
        httpRequest.setUrl(url);
        httpRequest.setMethod(HttpMethod.DELETE);
        return this;
    }

    public HttpActionBuilder uriVariables(Map<String, ?> uriVariables) {
        requireNonNull(uriVariables);
        httpRequest.setUriVariables(uriVariables);
        return this;
    }

    public HttpActionBuilder body(String body) {
        requireNonNull(body);
        httpRequest.setBody(body);
        return this;
    }

    public HttpRequest build() {
        return this.httpRequest;
    }

}
