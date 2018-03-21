package form3.service.endpoints.domain;

import java.util.Objects;

public class PaymentDataAttributes {
    private String   amount;
    private String currency;

    public PaymentDataAttributes(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDataAttributes that = (PaymentDataAttributes) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(amount, currency);
    }
}
