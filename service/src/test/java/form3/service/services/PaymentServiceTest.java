package form3.service.services;

import form3.service.TestContext;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PaymentServiceTest implements TestContext{

    private PaymentRepository mockRepository = Mockito.mock(PaymentRepository.class);
    private PaymentService    service        = new PaymentServiceImp(mockRepository);

    @Test
    public void shouldFindByIdWithSuccessIfExists(){
        when(mockRepository.findById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(Optional.of(samplePayment1));
        MatcherAssert.assertThat(service.findById(samplePaymentId1), equalTo(Optional.of(samplePayment1)));
    }

    @Test
    public void shouldFindByIdWithOptionalEmptyIfNotExists(){
        when(mockRepository.findById(org.mockito.Matchers.eq(samplePaymentId1)))
                .thenReturn(Optional.empty());
        MatcherAssert.assertThat(service.findById(samplePaymentId1), equalTo(Optional.empty()));
    }

    @Test
    public void shouldFindAll(){
        Pageable pageable = PageRequest.of(sampleCriteria.getPage(), sampleCriteria.getSize());

        when(mockRepository.findAll(org.mockito.Matchers.eq(pageable)))
            .thenReturn(new PageImpl<>(samplePaymentList) );
        MatcherAssert.assertThat(service.findAll(sampleCriteria), contains(samplePayment1, samplePayment2));
    }

    @Test
    public void shouldCreateOrUpdateWithSuccess(){
        when(mockRepository.save(org.mockito.Matchers.eq(samplePayment1)))
            .thenReturn( samplePayment1 );
        MatcherAssert.assertThat(service.createOrUpdate(samplePayment1), equalTo(samplePayment1));
    }

    @Test
    public void shouldDeleteWithSuccessIfExists(){
        when(mockRepository.findById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(Optional.of(samplePayment1));

        doNothing()
            .when(mockRepository)
            .deleteById(org.mockito.Matchers.eq(samplePaymentId1));

        MatcherAssert.assertThat(service.deleteById(samplePaymentId1), equalTo(true));
    }

    @Test
    public void shouldDeleteWithSuccessIfDoesNotExists(){
        when(mockRepository.findById(org.mockito.Matchers.eq(samplePaymentId1)))
            .thenReturn(Optional.empty());

        doNothing()
            .when(mockRepository)
            .deleteById(org.mockito.Matchers.eq(samplePaymentId1));

        MatcherAssert.assertThat(service.deleteById(samplePaymentId1), equalTo(false));
    }
}
