package form3.service.services;

import form3.service.domain.Payment;
import form3.service.services.criteria.FindAllCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImp(PaymentRepository repository) {
        this.repository = repository;
    }

    /**
     * List payments given a FindAllCriteria object.
     *
     * @param criteria Criteria used to filter and paginate the data
     * @return
     *  Page of payments
     */
    @Override
    public Collection<Payment> findAll(FindAllCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        return repository.findAll(pageable).getContent();
    }

    /**
     * This method is used to find a payment by Id.
     *
     * It is assumed that the Ids are unique and it is impossible to have multiple
     * payments with the same paymentId.
     *
     * @param id Payment Id
     *
     * @return returns a payment if exists
     */
    @Override
    public Optional<Payment> findById(@NotNull  String id) {
        return repository.findById(id);
    }

    /**
     * This method is used to delete a payment given a paymentId
     * @param id Payment Id
     * @return true if the payment was removed successfully, false otherwise
     */
    @Override
    public boolean deleteById(@NotNull final String id) {
        return repository.findById(id).map( x -> {
            repository.deleteById(id);
            return true;
        })
        .orElse(false);
    }

    @Override
    public Payment createOrUpdate(@NotNull Payment payment) {
        repository.save(payment);
        return payment;
    }
}
