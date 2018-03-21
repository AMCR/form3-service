package form3.service.endpoints;

import form3.service.domain.Payment;
import form3.service.endpoints.domain.PaymentData;
import form3.service.endpoints.domain.PaymentDataMapper;
import form3.service.endpoints.domain.RequestFetchPayment;
import form3.service.endpoints.domain.ResponsePaymentList;
import form3.service.services.IdGeneratorService;
import form3.service.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Collection;

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
    public ResponseEntity<ResponsePaymentList> findAll(@PathParam("version") String version, @Valid @RequestBody RequestFetchPayment request){
        Collection<Payment> result = service.findAll(request.toFindAllCriteria());
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
    public ResponseEntity<ResponsePaymentList> findById(@PathParam("version") final String version, @PathParam("id") @Valid @NotNull final String id ){
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
    public ResponseEntity<ResponsePaymentList> createOrUpdate(@PathParam("version") final String version, @Valid @RequestBody PaymentData request){
        int statusCode    = request.getId().isPresent() ? 200 : 201;

        Payment input     = PaymentDataMapper.withVersionMapToPayment(version, request);
        String  paymentId = input.getId().orElse(idService.getNewId());

        input.setId(paymentId);
        Payment result = service.createOrUpdate(input);

        return ResponseEntity.status(statusCode).body(ResponsePaymentList.builder()
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
    public ResponseEntity<ResponsePaymentList> deleteById(@PathParam("version") final String version, @PathParam("id") @Valid @NotNull final String id) {
        return service.deleteById(id) ? SuccessResponse : NotFountResponse;
    }
}

