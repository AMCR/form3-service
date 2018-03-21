package form3.service.domain;

import java.util.Objects;

@SuppressWarnings("ALL")
public class Payment {

    private String id;
    private PaymentType type;
    private int version;
    private String organizationId;
    private PaymentAttributes attributes;


    public Payment(String id, PaymentType type, int version, String organizationId, PaymentAttributes attributes) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.organizationId = organizationId;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public PaymentAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return version == payment.version &&
                Objects.equals(id, payment.id) &&
                type == payment.type &&
                Objects.equals(organizationId, payment.organizationId) &&
                Objects.equals(attributes, payment.attributes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, version, organizationId, attributes);
    }
}
