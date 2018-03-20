package form3.service;

import form3.service.components.ServiceComponent;
import org.hamcrest.Matchers;
import org.junit.Test;

public class HealthEndpointTest {

    @Test
    public void checkHealthEndpointIsExposed(){
        ServiceComponent serviceComponent = Environment.getInstance().getServiceComponent();

        serviceComponent.whenCallHealthEndpoint()
            .then()
                .statusCode(200)
                .body("status", Matchers.equalTo("UP"));
    }
}
