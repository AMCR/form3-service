package form3.service.endpoints.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import form3.service.domain.Payment;
import form3.service.domain.PaymentAttributes;
import form3.service.domain.PaymentType;
import form3.service.services.IdGeneratorService;

import java.io.IOException;
import java.util.Currency;
import java.util.Optional;

@SuppressWarnings("ALL")
public class PaymentDataMapper {
    private static ObjectMapper mapper = new ObjectMapper();
    private PaymentDataMapper(){
    }

    /**
     * Convert PaymentData -> Payment Domain
     */
    public static PaymentData withVersionMapToPaymentData(String version, Payment payment) {
        if( "v1".equals(version) ){
            return mapToPaymentDataV1(payment);
        }
        throw new IllegalArgumentException(String.format("Invalid version '%s'", version));
    }

    public static PaymentData withVersionMapToPaymentData(String version, String payload) throws IOException {
        if( "v1".equals(version) ){
            return mapper.readValue(payload, PaymentDataV1.class);
        }
        throw new IllegalArgumentException(String.format("Invalid version '%s'", version));
    }

    private static PaymentDataV1 mapToPaymentDataV1(Payment payment){
        return new PaymentDataV1(
                Optional.of(payment.getId()),
                payment.getType().getName(),
                payment.getVersion(),
                payment.getOrganizationId(),
                mapToPaymentDataAttributesV1(payment.getAttributes())
        );
    }

    private static PaymentDataAttributes mapToPaymentDataAttributesV1(PaymentAttributes attributes){
        return new PaymentDataAttributes(
                attributes.getAmount(),
                attributes.getCurrency().getCurrencyCode()
        );
    }

    /**
     * Convert PaymentData <- Payment Domain
     */
    public static Payment withVersionMapToPayment(String version, PaymentData payment) {
        if( "v1".equals(version) && payment instanceof PaymentDataV1 ){
            return mapToPayment((PaymentDataV1) payment);
        }
        throw new IllegalArgumentException(String.format("Invalid version '%s'", version));
    }

    private static Payment mapToPayment( PaymentDataV1 data){
        return new Payment(
                data.getId().get(), //At this point we must have a paymentId
                PaymentType.valueOf(data.getType()),
                data.getVersion(),
                data.getOrganizationId(),
                mapToPaymentAttributes(data.getAttributes())
        );
    }

    private static PaymentAttributes mapToPaymentAttributes(PaymentDataAttributes attributes) {
        return new PaymentAttributes(
                attributes.getAmount(),
                Currency.getInstance(attributes.getCurrency())
        );
    }
}
