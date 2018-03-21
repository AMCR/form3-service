package form3.service.endpoints.domain;

import java.util.Objects;

public class ResponsePaymentDataLinks {
    private String self;

    public ResponsePaymentDataLinks(String self) {
        this.self = self;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsePaymentDataLinks that = (ResponsePaymentDataLinks) o;
        return Objects.equals(self, that.self);
    }

    @Override
    public int hashCode() {

        return Objects.hash(self);
    }
}
