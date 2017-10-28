package project.dao;

import project.model.query.Property;
import project.model.query.SearchParams;

import java.util.*;

/** Класс для преобразования запроса, чтобы он учитывал поисковые ограничения (фильтры). */
public class SearchParamsProcessor {

    public static ProcessResult process(String query, SearchParams searchParams) {
        if (searchParams == null) {
            return new ProcessResult(query, Collections.emptyMap());
        }

        StringBuilder builder = new StringBuilder(query);
        Map<String, Object> params = new HashMap<>();

        List<Property> filters = searchParams.getFilter();
        if (filters != null && !filters.isEmpty()) {

            // Получим фильтры сгруппированнаы по наименованию.
            // Фильтры в рамках одной группы буду объединены с помощью ИЛИ.
            // Группы будут объединятся с помощью И.
            Map<String, List<Property>> groupedFilters = new LinkedHashMap<>();

            filters.forEach(filter -> {
                String name = filter.getProperty();
                List<Property> values = groupedFilters.get(name);
                if (values == null) {
                    values = new ArrayList<>();
                    groupedFilters.put(name, values);
                }
                values.add(filter);
            });


            int index = 0;
            builder.append(" WHERE ");

            Iterator<List<Property>> groupIterator = groupedFilters.values().iterator();

            while (groupIterator.hasNext()) {
                List<Property> list = groupIterator.next();

                if (list.size() == 0) {
                    throw new IllegalStateException("Empty list of filters with same name");
                }

                if (list.size() > 1) {
                    builder.append("(");
                }

                Iterator<Property> iterator = list.iterator();

                while (iterator.hasNext()) {
                    Property filter = iterator.next();

                    String paramName = filter.getProperty() + "_param_" + ++index;
                    params.put(paramName, filter.getValue());

                    switch (filter.getOperator()) {
                        case "=":
                            builder.append(filter.getProperty()).append(" = :").append(paramName);
                            break;
                        case "like":
                            builder.append(filter.getProperty()).append(" LIKE '%' || :").append(paramName).append(" || '%'");
                            break;
                        default:
                            throw new IllegalStateException("Unsupported operator: " + filter.getOperator());
                    }

                    if (iterator.hasNext()) {
                        builder.append(" OR "); // Все фильтры для одинакового поля соединяем через ИЛИ (OR).
                    }
                }

                if (list.size() > 1) {
                    builder.append(")");
                }

                if (groupIterator.hasNext()) {
                    builder.append(" AND "); // объединим группы фильтров.
                }
            }

        }

        builder.append(" LIMIT ").append(searchParams.getLimit() == 0 ? "ALL" : searchParams.getLimit());
        builder.append(" OFFSET ").append(searchParams.getStart());

        return new ProcessResult(builder.toString(), params);
    }

    /** Результат обработки запроса с учетом поисковых ограничений (фильтров). */
    public static class ProcessResult {

        /** Итоговой запрос. */
        private String resultQuery;

        /** Параметры для подстановки в запрос. */
        private Map<String, Object> params;

        ProcessResult(String resultQuery, Map<String, Object> params) {
            this.resultQuery = resultQuery;
            this.params = params;
        }

        public String getResultQuery() {
            return resultQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

    }

}
