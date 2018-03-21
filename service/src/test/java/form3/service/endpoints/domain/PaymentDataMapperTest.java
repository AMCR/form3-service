package form3.service.endpoints.domain;

import form3.service.TestContext;
import form3.service.domain.Payment;
import org.junit.Test;

import static form3.service.endpoints.domain.PaymentDataMapper.withVersionMapToPayment;
import static form3.service.endpoints.domain.PaymentDataMapper.withVersionMapToPaymentData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PaymentDataMapperTest implements TestContext{

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailMapToPaymentDataIfVersionNotSupported(){
        withVersionMapToPaymentData("v0", samplePayment1 );
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailMapToPaymentIfVersionNotSupported(){
        withVersionMapToPayment("v0", samplePaymentDataUpdated );
    }

    @Test
    public void shouldMapToPaymentDataV1(){
        PaymentData result = withVersionMapToPaymentData("v1", samplePayment1);
        assertThat(result, equalTo(samplePaymentDataUpdated));
    }

    @Test
    public void shouldMapToPayment(){
        Payment result = withVersionMapToPayment("v1", samplePaymentDataUpdated);
        assertThat(result, equalTo(samplePayment1));
    }
}
