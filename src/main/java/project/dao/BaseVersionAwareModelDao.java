package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import project.model.BaseVersionAwareModel;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/** Базовый класс DAO для моделей по которым ведется история. */
public abstract class BaseVersionAwareModelDao<MODEL extends BaseVersionAwareModel> implements AbstractDao<MODEL> {

    @Autowired
    protected DataSource ds;

    protected NamedParameterJdbcTemplate jdbc;

    @PostConstruct
    public void init() {
        jdbc = new NamedParameterJdbcTemplate(ds);
    }

    protected Map<String, Object> prepareParams(MODEL model) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", model.getId());
        params.put("version", model.getVersion());
        params.put("commentary", model.getCommentary());

        return params;
    }

    protected Map<String, Object> prepareHistoricalParams(MODEL model) {
        Map<String, Object> params = prepareParams(model);
        params.put("date", Timestamp.from(ZonedDateTime.now().toInstant()));
        params.put("version", model.getVersion() + 1);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        params.put("login", authentication.getName());
        params.put("ip", details.getRemoteAddress());

        return params;
    }


}
