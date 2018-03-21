package form3.service.endpoints.domain;

import form3.service.domain.Payment;
import form3.service.domain.PaymentAttributes;
import form3.service.domain.PaymentType;

import java.util.Currency;
import java.util.stream.Collectors;

public class PaymentDataMapper {
    private PaymentDataMapper(){
    }

    /**
     * Convert PaymentData -> Payment Domain
     */
    public static PaymentData withVersionMapToPaymentData(String version, Payment payment) {
        if( version.equals("v1") ){
            return mapToPaymentDataV1(payment);
        }
        throw new IllegalArgumentException(String.format("Invalid version '%s'", version));
    }

    private static PaymentDataV1 mapToPaymentDataV1(Payment payment){
        return new PaymentDataV1(
                payment.getId(),
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
        if( version.equals("v1") && payment instanceof PaymentDataV1 ){
            return mapToPayment((PaymentDataV1) payment);
        }
        throw new IllegalArgumentException(String.format("Invalid version '%s'", version));
    }

    private static Payment mapToPayment( PaymentDataV1 data ){
        return new Payment(
                data.getId(),
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
