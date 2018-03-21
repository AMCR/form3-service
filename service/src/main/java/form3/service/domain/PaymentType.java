package form3.service.domain;

public enum PaymentType {
    Payment("Payment");

    private String value;
    PaymentType(String value){
        this.value = value;
    }

    public String getName(){
        return value;
    }
}
