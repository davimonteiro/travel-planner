package br.uece.beethoven.engine;


import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class HttpAction implements Action {

    private String url;

    private HttpMethod method;

    private String body;

    private Map<String, ?> uriVariables;


    @Override
    public void process() { }

}
