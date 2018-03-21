package form3.service.endpoints.domain;

import form3.service.domain.Payment;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static form3.service.endpoints.domain.PaymentDataMapper.withVersionMapToPaymentData;

@SuppressWarnings("ALL")
public class ResponsePaymentList {

    private Collection<PaymentData> data;
    private ResponsePaymentDataLinks links;

    private ResponsePaymentList(Collection<PaymentData> data, ResponsePaymentDataLinks links){
        this.data = data;
        this.links = links;
    }

    public Collection<PaymentData> getData(){
        return data;
    }

    public ResponsePaymentDataLinks getLinks(){
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsePaymentList that = (ResponsePaymentList) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data, links);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final String DefaultVersion = "v1";
        private Collection<Payment> payments;
        private String version;
        private String link;

        private Builder(){
            this.payments = Collections.emptyList();
            this.version  = DefaultVersion;
        }

        public Builder withData(Collection<Payment> payments){
            this.payments = payments;
            return this;
        }

        public Builder withVersion(String version){
            this.version = version;
            return this;
        }

        public Builder withLink(String link){
            this.link = link;
            return this;
        }

        public ResponsePaymentList build(){
            return new ResponsePaymentList(
                payments.stream()
                    .map( item -> withVersionMapToPaymentData(version, item) )
                    .collect(Collectors.toList()),
                new ResponsePaymentDataLinks(link)
            );
        }
    }
}
