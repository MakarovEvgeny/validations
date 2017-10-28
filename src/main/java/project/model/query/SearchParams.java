package project.model.query;

import java.util.ArrayList;
import java.util.List;

/** Параметры для поиска, приходят с клиента. */
public class SearchParams {

    private int limit;

    private int page;

    private int start;

    /** Непосредственно фильтры. */
    private List<Property> filter = new ArrayList<>();

    public List<Property> getFilter() {
        return filter;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }

    public int getStart() {
        return start;
    }
}
