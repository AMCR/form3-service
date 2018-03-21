package form3.service.endpoints.domain;

import form3.service.services.criteria.FindAllCriteria;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RequestFetchPayment {
    @NotNull
    @Min(1)
    private int page;

    @NotNull
    @Min(1)
    private int size;

    public RequestFetchPayment(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public FindAllCriteria toFindAllCriteria(){
        return new FindAllCriteria(this.size, this.page);
    }
}
