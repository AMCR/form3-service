package form3.service.endpoints;

import form3.service.TestContext;
import form3.service.endpoints.domain.RequestFetchPayment;
import form3.service.endpoints.domain.ResponsePaymentList;
import form3.service.services.IdGeneratorService;
import form3.service.services.PaymentService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentEndpointsTest implements TestContext {

    private IdGeneratorService mockIdService      = mock(IdGeneratorService.class);
    private PaymentService     mockPaymentService = mock(PaymentService.class);
    private PaymentEndpoints   endpoints          = new PaymentEndpoints(mockPaymentService, mockIdService);

    private String version = "v1";

    @Test
    public void shouldFindByIdWithSuccessIfExist(){
        when(mockPaymentService.findById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(Optional.of(samplePayment1));

        ResponseEntity<ResponsePaymentList> result = endpoints.findById(version, samplePaymentId1);

        assertThat(result.getStatusCodeValue(), equalTo(200));
        assertThat(result.getBody(),            equalTo(sampleResponsePayment1));
    }

    @Test
    public void shouldBeFindByIdWithFailureIfNotExist(){
        when(mockPaymentService.findById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(Optional.empty());

        ResponseEntity<ResponsePaymentList> result = endpoints.findById(version, samplePaymentId1);

        assertThat(result.getStatusCodeValue(), equalTo(404));
        assertThat(result.hasBody(),            equalTo(false));
    }

    @Test
    public void shouldBeFindAllWithSuccess(){
        when(mockPaymentService.findAll(org.mockito.Matchers.eq(sampleCriteria)))
            .thenReturn(samplePaymentList);

        ResponseEntity<ResponsePaymentList> result = endpoints.findAll(version, sampleCriteria.getSize(), sampleCriteria.getPage());

        assertThat(result.getStatusCodeValue(), equalTo(200));
        assertThat(result.getBody(),            equalTo(sampleResponsePaymentList));
    }

    @Test
    public void shouldBeCreateWithSuccess(){
        when(mockIdService.getNewId())
            .thenReturn(samplePaymentId1);

        when(mockPaymentService.createOrUpdate(org.mockito.Matchers.eq(samplePayment1)))
            .thenReturn(samplePayment1);

        ResponseEntity<ResponsePaymentList> result = endpoints.createOrUpdate(version, fromPayload("/payment-data-new.json"));

        assertThat(result.getStatusCodeValue(), equalTo(201));
        assertThat(result.getBody(),            equalTo(sampleResponsePayment1));
    }

    @Test
    public void shouldBeUpdateWithSuccess(){
        when(mockPaymentService.createOrUpdate(org.mockito.Matchers.eq(samplePayment1)))
            .thenReturn(samplePayment1);

        ResponseEntity<ResponsePaymentList> result = endpoints.createOrUpdate(version, fromPayload("/payment-data.json"));

        assertThat(result.getStatusCodeValue(), equalTo(200));
        assertThat(result.getBody(),            equalTo(sampleResponsePayment1));
    }


    @Test
    public void shouldBeDeleteWithSuccessIfExist(){
        when(mockPaymentService.deleteById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(true);

        ResponseEntity<ResponsePaymentList> result = endpoints.deleteById(version, samplePaymentId1);

        assertThat(result.getStatusCodeValue(), equalTo(200));
        assertThat(result.hasBody(),            equalTo(false));
    }


    @Test
    public void shouldBeDeleteWithFailureIfNotExist(){
        when(mockPaymentService.deleteById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(false);

        ResponseEntity<ResponsePaymentList> result = endpoints.deleteById(version, samplePaymentId1);

        assertThat(result.getStatusCodeValue(), equalTo(404));
        assertThat(result.hasBody(),            equalTo(false));
    }

    public String fromPayload(String fileName){
        try {
            return new String(readAllBytes(Paths.get(getClass().getResource(fileName).toURI())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
