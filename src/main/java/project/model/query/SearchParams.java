package project.model.query;

import java.util.ArrayList;
import java.util.List;

/** Параметры для поиска, приходят с клиента. */
public class SearchParams {

    private Integer limit;

    private Integer page;

    private Integer start;

    /** Непосредственно фильтры. */
    private List<Property> filter = new ArrayList<>();

    public List<Property> getFilter() {
        return filter;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getStart() {
        return start;
    }
}
