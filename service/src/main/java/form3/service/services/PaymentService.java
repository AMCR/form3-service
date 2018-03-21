package form3.service.services;

import form3.service.domain.Payment;
import form3.service.services.criteria.FindAllCriteria;

import java.util.Collection;
import java.util.Optional;

public interface PaymentService {

    Collection<Payment> findAll(FindAllCriteria criteria);

    Optional<Payment> findById(String id);

    boolean deleteById( String id );

    Payment createOrUpdate( Payment payment );
}
