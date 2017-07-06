package br.uece.beethoven.engine.dsl;


import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class HttpRequest {

    private String url;

    private HttpMethod method;

    private String body;

    private Map<String, ?> uriVariables;

}
