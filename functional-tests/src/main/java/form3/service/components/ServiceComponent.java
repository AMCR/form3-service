package form3.service.components;

import com.typesafe.config.Config;
import io.restassured.response.Response;

public abstract class ServiceComponent extends Component {

    protected ServiceComponent(Config config) {
        super(config);
    }

    public Response whenCallHealthEndpoint(){
        return given().get("/health");
    }

    public static ServiceComponent getInstance(Config config, Boolean functionalTestMode){
        return functionalTestMode ? new ServiceComponentFt(config) : new ServiceComponentIt(config);
    }
}

/**
 * Helper class to run the Functional Tests
 */
class ServiceComponentFt extends ServiceComponent {
    ServiceComponentFt(Config config){
        super(config);
    }
}

/**
 * Helper class to run the Integration Tests
 */
class ServiceComponentIt extends ServiceComponent {
    ServiceComponentIt(Config config){
        super(config);
    }
}
