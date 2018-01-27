package project.model.query;

public interface SqlAware {

    void appendSql(StringBuilder builder, String property, String paramName);

}
