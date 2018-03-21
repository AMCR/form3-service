package form3.service.endpoints;

import form3.service.domain.Payment;
import form3.service.endpoints.domain.PaymentData;
import form3.service.endpoints.domain.PaymentDataMapper;
import form3.service.endpoints.domain.ResponsePaymentList;
import form3.service.services.IdGeneratorService;
import form3.service.services.PaymentService;
import form3.service.services.criteria.FindAllCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static form3.service.endpoints.domain.PaymentDataMapper.withVersionMapToPaymentData;
import static java.lang.String.format;
import static java.util.Collections.singletonList;

@SuppressWarnings("ALL")
@RestController
public class PaymentEndpoints {

    private static final ResponseEntity<ResponsePaymentList> NotFountResponse = ResponseEntity.status(404).build();
    private static final ResponseEntity<ResponsePaymentList> SuccessResponse  = ResponseEntity.status(200).build();

    private final PaymentService service;
    private final IdGeneratorService idService;

    public PaymentEndpoints(PaymentService paymentService, IdGeneratorService idService){
        this.service   = paymentService;
        this.idService = idService;
    }

    @RequestMapping(
        value = "/{version}/payments",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public ResponseEntity<ResponsePaymentList> findAll(
            @PathVariable("version") String version,
            @RequestParam(value = "pageSize", defaultValue = "10") @Valid @Min(1) int size,
            @RequestParam(value = "page",     defaultValue = "1" ) @Valid @Min(1) int page
    ){
        Collection<Payment> result = service.findAll(new FindAllCriteria(size, page));
        return ResponseEntity.ok(ResponsePaymentList.builder()
            .withData   (result)
            .withLink   (format("/%s/payments", version))
            .withVersion(version)
            .build()
        );
    }

    @RequestMapping(
        value = "/{version}/payments/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public ResponseEntity<ResponsePaymentList> findById(
            @PathVariable("version") final String version,
            @PathVariable("id") @Valid @NotNull final String id ){
        return service.findById(id)
            .map( payment -> ResponseEntity.ok(ResponsePaymentList.builder()
                .withData   (singletonList(payment))
                .withLink   (format("/%s/payments/%s", version, id))
                .withVersion(version)
                .build())
            )
            .orElse( NotFountResponse ) ;
    }

    @RequestMapping(
        value = "/{version}/payments",
        method = RequestMethod.POST,
        produces = "application/json"
    )
    public ResponseEntity<ResponsePaymentList> createOrUpdate(
        @PathVariable("version") final String version,
        @RequestBody String payload
    ){
        PaymentData request;
        boolean isUpdate;

        try {
            request = withVersionMapToPaymentData(version, payload);
        }catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        isUpdate = request.getId().isPresent();
        if( !isUpdate ){
            request.setId(Optional.of(idService.getNewId()));
        }
        Payment input     = PaymentDataMapper.withVersionMapToPayment(version, request);
        String  paymentId = input.getId();

        input.setId(paymentId);
        Payment result = service.createOrUpdate(input);
        return ResponseEntity.status(isUpdate ? 200 : 201).body(ResponsePaymentList.builder()
            .withData   (singletonList(result))
            .withLink   (format("/%s/payments/%s", version, paymentId))
            .withVersion(version)
            .build()
        );
    }

    @RequestMapping(
        value = "/{version}/payments/{id}",
        method = RequestMethod.DELETE
    )
    public ResponseEntity<ResponsePaymentList> deleteById(
        @PathVariable("version") final String version,
        @PathVariable("id") final String id
    ) {
        return service.deleteById(id) ? SuccessResponse : NotFountResponse;
    }
}

