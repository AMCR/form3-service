package form3.service;

import form3.service.domain.Payment;
import form3.service.domain.PaymentAttributes;
import form3.service.domain.PaymentType;
import form3.service.endpoints.domain.PaymentDataAttributes;
import form3.service.endpoints.domain.PaymentDataV1;
import form3.service.endpoints.domain.ResponsePaymentList;
import form3.service.services.criteria.FindAllCriteria;

import java.util.*;

public interface TestContext {

    String  samplePaymentId1 = "11111111-1111-1111-1111-111111111111";
    String  samplePaymentId2 = "22222222-2222-2222-2222-222222222222";

    Payment samplePayment1 = new Payment(
        samplePaymentId1,
        PaymentType.Payment,
        0,
        "11111111-1111-1111-1111-111111111111",
        new PaymentAttributes(
                "1.00",
                Currency.getInstance("USD")
        )
    );
    Payment samplePayment2 = new Payment(
        samplePaymentId2,
        PaymentType.Payment,
        0,
        "11111111-1111-1111-1111-111111111111",
        new PaymentAttributes(
            "1.00",
            Currency.getInstance("USD")
        )
    );

    PaymentDataV1 samplePaymentDataNew = new PaymentDataV1(
        Optional.empty(),
        "Payment",
        0,
        "11111111-1111-1111-1111-111111111111",
        new PaymentDataAttributes(
            "1.00",
            "USD"
        )
    );
    PaymentDataV1 samplePaymentDataUpdated = new PaymentDataV1(
        Optional.of(samplePaymentId1),
        "Payment",
        0,
        "11111111-1111-1111-1111-111111111111",
        new PaymentDataAttributes(
                "1.00",
                "USD"
        )
    );

    List<Payment> samplePaymentList = Arrays.asList(samplePayment1, samplePayment2);

    FindAllCriteria sampleCriteria = new FindAllCriteria(10, 1);

    ResponsePaymentList sampleResponsePayment1 = ResponsePaymentList.builder()
        .withData(Collections.singletonList(samplePayment1))
        .withLink(String.format("/v1/payments/%s", samplePaymentId1))
        .build();

    ResponsePaymentList sampleResponsePaymentList = ResponsePaymentList.builder()
        .withData(Arrays.asList(samplePayment1, samplePayment2))
        .withLink("/v1/payments")
        .build();
}
