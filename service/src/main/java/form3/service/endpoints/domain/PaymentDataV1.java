package form3.service.endpoints.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("ALL")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDataV1 implements PaymentData{

    @JsonProperty("id")
    private Optional<String> id;
    @JsonProperty(value = "type", required = true)
    private String type;
    @JsonProperty(value = "version", required = true)
    private int version;
    @JsonProperty(value = "organisation_id", required = true)
    private String organizationId;
    @JsonProperty(value = "attributes", required = true)
    private PaymentDataAttributes attributes;

    public PaymentDataV1() {
        this.id = Optional.empty();
    }

    public void setId(String id){
        this.id = Optional.ofNullable(id);
    }

    public PaymentDataV1(Optional<String> id, String type, int version, String organizationId, PaymentDataAttributes attributes) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.organizationId = organizationId;
        this.attributes = attributes;
    }

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public PaymentDataAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentDataAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDataV1 that = (PaymentDataV1) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, version, organizationId, attributes);
    }
}
