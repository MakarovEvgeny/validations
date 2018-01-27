package project.model.query;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Поисковый оператор. */
public enum Operator implements SqlAware {

    @JsonProperty("=")
    EQUALS {
        @Override
        public void appendSql(StringBuilder builder, String property, String paramName) {
            builder.append(property).append(" = :").append(paramName);
        }
    },

    @JsonProperty("like")
    LIKE {
        @Override
        public void appendSql(StringBuilder builder, String property, String paramName) {
            builder.append(property).append(" LIKE '%' || :").append(paramName).append(" || '%'");
        }
    }

}
