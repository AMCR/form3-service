package form3.service.stub;

import form3.service.domain.Payment;
import form3.service.services.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentRepositoryStub implements PaymentRepository {

    private final LinkedHashMap<String, Payment> inMemoryStorage = new LinkedHashMap<>();

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        int offset   = pageable.getPageSize() * (pageable.getPageNumber() - 1);
        int pageSize = pageable.getPageSize();

        List<Payment> data = inMemoryStorage.values().stream()
            .skip   (offset)
            .limit  (pageSize)
            .collect(Collectors.toList());
        return new PageImpl<>(data);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(inMemoryStorage.get(id));
    }

    @Override
    public Payment save(Payment payment) {
        return inMemoryStorage.put(payment.getId(), payment);
    }

    @Override
    public void deleteById(String id) {
        inMemoryStorage.remove(id);
    }
}
