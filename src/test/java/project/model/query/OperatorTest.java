package project.model.query;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static project.model.query.Operator.*;

public class OperatorTest {

    private StringBuilder builder;

    @Before
    public void setUp() {
        builder = new StringBuilder();
    }

    @Test
    public void equalsTest() throws Exception {
        EQUALS.appendSql(builder, "column", "column_param_1");
        assertEquals("column = :column_param_1", builder.toString());
    }

    @Test
    public void likeTest() throws Exception {
        LIKE.appendSql(builder, "column", "column_param_1");
        assertEquals("column LIKE '%' || :column_param_1 || '%'", builder.toString());
    }

    @Test
    public void likeIgnoreCase() throws Exception {
        ILIKE.appendSql(builder, "column", "column_param_1");
        assertEquals("UPPER(column) LIKE '%' || UPPER(:column_param_1) || '%'", builder.toString());
    }

    @Test
    public void ftsTest() throws Exception {
        FTS.appendSql(builder, "column", "column_param_1");
        assertEquals("TO_TSVECTOR('russian', column) @@ PLAINTO_TSQUERY('russian', :column_param_1)", builder.toString());
    }

}