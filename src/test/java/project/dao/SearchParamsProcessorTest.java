package project.dao;

import org.junit.Test;
import project.dao.SearchParamsProcessor.ProcessResult;
import project.model.query.Operator;
import project.model.query.Property;
import project.model.query.SearchParams;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static project.model.query.Operator.LIKE;

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

    private Operator prepareOperatorMock() {
        Operator operator = mock(Operator.class);
        doAnswer(invocation -> {
            StringBuilder builder = invocation.getArgumentAt(0, StringBuilder.class);
            builder.append("criteria");
            return Void.TYPE;
        }).when(operator).appendSql(any(StringBuilder.class), anyString(), anyString());

        return operator;
    }

    @Test
    public void testOperatorMock() throws Exception {
        Operator operator = prepareOperatorMock();

        StringBuilder builder = new StringBuilder();
        operator.appendSql(builder, null, null);

        assertEquals("criteria", builder.toString());
    }

    @Test
    public void singleFilterTest() {
        SearchParams params = getSearchParamsWithSingleFilter(new Property("field", prepareOperatorMock(), "a"));
        ProcessResult result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE criteria LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertEquals(result.getParams().get("field_param_1"), "a");

        params = getSearchParamsWithSingleFilter(new Property("field", LIKE, "value"));
        result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE field LIKE '%' || :field_param_1 || '%' LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertEquals(result.getParams().get("field_param_1"), "value");
    }


    @Test
    public void multipleFiltersTest() {
        SearchParams params = getSearchParamsWithSingleFilter(new Property("field", prepareOperatorMock(), "a"), new Property("field", prepareOperatorMock(), "b"));
        ProcessResult result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE (criteria OR criteria) LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field_param_1"));
        assertTrue(result.getParams().containsKey("field_param_2"));
        assertEquals(result.getParams().get("field_param_1"), "a");
        assertEquals(result.getParams().get("field_param_2"), "b");


        params = getSearchParamsWithSingleFilter(new Property("field1", prepareOperatorMock(), "a"), new Property("field2", prepareOperatorMock(), "b"), new Property("field3", prepareOperatorMock(), "c"));
        result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE criteria AND criteria AND criteria LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field1_param_1"));
        assertTrue(result.getParams().containsKey("field2_param_2"));
        assertTrue(result.getParams().containsKey("field3_param_3"));
        assertEquals(result.getParams().get("field1_param_1"), "a");
        assertEquals(result.getParams().get("field2_param_2"), "b");
        assertEquals(result.getParams().get("field3_param_3"), "c");


        params = getSearchParamsWithSingleFilter(new Property("field1", prepareOperatorMock(), "a"), new Property("field1", prepareOperatorMock(), "b"), new Property("field2", prepareOperatorMock(), "c") , new Property("field2", prepareOperatorMock(), "d"));
        result = SearchParamsProcessor.process("", params);
        assertEquals(" WHERE (criteria OR criteria) AND (criteria OR criteria) LIMIT ALL OFFSET 0", result.getResultQuery());
        assertTrue(result.getParams().containsKey("field1_param_1"));
        assertTrue(result.getParams().containsKey("field1_param_2"));
        assertTrue(result.getParams().containsKey("field2_param_3"));
        assertTrue(result.getParams().containsKey("field2_param_4"));
        assertEquals(result.getParams().get("field1_param_1"), "a");
        assertEquals(result.getParams().get("field1_param_2"), "b");
        assertEquals(result.getParams().get("field2_param_3"), "c");
        assertEquals(result.getParams().get("field2_param_4"), "d");
    }

}