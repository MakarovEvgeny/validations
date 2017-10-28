package project.dao;

import org.junit.Test;
import project.dao.SearchParamsProcessor.ProcessResult;
import project.model.query.Property;
import project.model.query.SearchParams;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SearchParamsProcessorTest {


    @Test
    public void limitAndOffsetDefaultTest() {
        SearchParams params = new SearchParams();
        ProcessResult result = SearchParamsProcessor.process("", params);

        assertEquals(" LIMIT ALL OFFSET 0", result.getResultQuery());
    }

    private SearchParams getSearchParamsWithSingleFilter(Property property) {
        SearchParams params = new SearchParams();
        List<Property> filter = params.getFilter();
        filter.add(property);

        return params;
    }

    private SearchParams getSearchParamsWithSingleFilter(Property... properties) {
        SearchParams params = new SearchParams();
        List<Property> filter = params.getFilter();
        filter.addAll(Arrays.asList(properties));

        return params;
    }

    @Test
    public void singleFilterTest() {
        SearchParams params = getSearchParamsWithSingleFilter(new Property("field", "=", "a"));
        ProcessResult result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE field = :field_param_1 LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertEquals(result.getParams().get("field_param_1"), "a");

        params = getSearchParamsWithSingleFilter(new Property("field", "like", "value"));
        result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE field LIKE '%' || :field_param_1 || '%' LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertEquals(result.getParams().get("field_param_1"), "value");
    }


    @Test
    public void multipleFiltersTest() {
        SearchParams params = getSearchParamsWithSingleFilter(new Property("field", "=", "a"), new Property("field", "=", "b"));
        ProcessResult result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE (field = :field_param_1 OR field = :field_param_2) LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertTrue(result.getParams().containsKey("field_param_2"));
        assertEquals(result.getParams().get("field_param_1"), "a");
        assertEquals(result.getParams().get("field_param_2"), "b");


        params = getSearchParamsWithSingleFilter(new Property("field1", "=", "a"), new Property("field2", "=", "b"), new Property("field3", "like", "c"));
        result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE field1 = :field1_param_1 AND field2 = :field2_param_2 AND field3 LIKE '%' || :field3_param_3 || '%' LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field1_param_1"));
        assertTrue(result.getParams().containsKey("field2_param_2"));
        assertTrue(result.getParams().containsKey("field3_param_3"));
        assertEquals(result.getParams().get("field1_param_1"), "a");
        assertEquals(result.getParams().get("field2_param_2"), "b");
        assertEquals(result.getParams().get("field3_param_3"), "c");
    }

}