package form3.service;

import form3.service.components.ServiceComponent;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

public class FetchEndpointTest {

    @Test
    @Ignore
    public void whenFetchingAnExistingPaymentMustGetSuccess(){
        String paymentId = "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43";
        ServiceComponent serviceComponent = Environment.getInstance().getServiceComponent();

        serviceComponent.whenFetchingPaymentBy(paymentId)
            .then()
            .statusCode(200)
            .body("data.id",    Matchers.equalTo(paymentId))
            .body("data.type",  Matchers.equalTo("Payment") )
            .body("links.self", Matchers.endsWith("/v1/payments/"+paymentId) )
        ;
    }

    @Test
    @Ignore
    public void whenFetchingAnNonExistingPaymentMustGetFailure(){
        String paymentId = "11111111-1111-1111-1111-111111111111";
        ServiceComponent serviceComponent = Environment.getInstance().getServiceComponent();

        serviceComponent.whenFetchingPaymentBy(paymentId)
            .then()
            .statusCode(404)
        ;
    }

    @Test
    @Ignore
    public void whenFetchingListPaymentsMustGetSuccess(){
        ServiceComponent serviceComponent = Environment.getInstance().getServiceComponent();

        serviceComponent.whenFetchingListPayments()
            .then()
            .statusCode(200)
        ;
    }
}
