package br.uece.beethoven.engine;

import org.springframework.http.HttpMethod;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public class HttpActionBuilder {

    private HttpAction httpAction;

    public HttpActionBuilder() {
        this.httpAction = new HttpAction();
    }

    public static HttpActionBuilder request() {
        return new HttpActionBuilder();
    }

    public HttpActionBuilder get(String url) {
        requireNonNull(url);
        httpAction.setUrl(url);
        httpAction.setMethod(HttpMethod.GET);
        return this;
    }

    public HttpActionBuilder post(String url) {
        requireNonNull(url);
        httpAction.setUrl(url);
        httpAction.setMethod(HttpMethod.POST);
        return this;
    }

    public HttpActionBuilder put(String url) {
        requireNonNull(url);
        httpAction.setUrl(url);
        httpAction.setMethod(HttpMethod.PUT);
        return this;
    }

    public HttpActionBuilder delete(String url) {
        requireNonNull(url);
        httpAction.setUrl(url);
        httpAction.setMethod(HttpMethod.DELETE);
        return this;
    }

    public HttpActionBuilder uriVariables(Map<String, ?> uriVariables) {
        requireNonNull(uriVariables);
        httpAction.setUriVariables(uriVariables);
        return this;
    }

    public HttpActionBuilder body(String body) {
        requireNonNull(body);
        httpAction.setBody(body);
        return this;
    }

    public HttpAction build() {
        return this.httpAction;
    }

}
