package form3.service.components;

import com.typesafe.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class Component {

    protected final Config config;
    protected final String baseUrl;

    Component(Config config){
        this.config  = config;
        this.baseUrl = config.getString("baseUrl");
    }

    public RequestSpecification given(){
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(baseUrl).build();
        return RestAssured.given(spec);
    }
}
