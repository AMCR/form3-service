package form3.service.services;

import form3.service.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PaymentRepository extends Repository<Payment, String> {

    Page<Payment> findAll(Pageable pageable);

    Optional<Payment> findById(String id);

    Payment save(Payment payment);

    void deleteById(String s);

}
