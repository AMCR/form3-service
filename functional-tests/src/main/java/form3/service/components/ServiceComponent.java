package form3.service.components;

import com.typesafe.config.Config;
import io.restassured.response.Response;

public abstract class ServiceComponent extends Component {

    ServiceComponent(Config config) {
        super(config);
        System.out.println("Running ServiceComponent with baseUrl: "+baseUrl);
    }

    public static ServiceComponent getInstance(Config config, Boolean functionalTestMode){
        return functionalTestMode ? new ServiceComponentFt(config) : new ServiceComponentIt(config);
    }

    public Response whenCallHealthEndpoint(){
        return given().get("/health");
    }

    public Response whenFetchingPaymentBy(String paymentId) {
        return given().get(String.format("/v1/payments/%s", paymentId));
    }

    public Response whenDeletingPaymentBy(String paymentId) {
        return given().delete(String.format("/v1/payments/%s", paymentId));
    }

    public Response whenFetchingListPayments() {
        return given().get("/v1/payments");
    }

    public Response whenCreatingPayment(String payload) {
        return given()
            .body( payload )
            .post("/v1/payments");
    }

    public Response whenUpdatingPayment(String payload) {
        return given()
                .body( payload )
                .post("/v1/payments");
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
