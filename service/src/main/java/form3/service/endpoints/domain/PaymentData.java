package form3.service.endpoints.domain;

import java.util.Optional;

public interface PaymentData {
    Optional<String> getId();
    void setId(Optional<String> id);
}
