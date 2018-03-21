package form3.service.services.criteria;

import java.util.Objects;

public class FindAllCriteria {
    private int size;
    private int page;

    public FindAllCriteria(int size, int page) {
        this.size = size;
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindAllCriteria that = (FindAllCriteria) o;
        return size == that.size &&
                page == that.page;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, page);
    }
}
