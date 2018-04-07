package project.dao.eventlog;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import project.model.eventlog.EventLog;

import static project.dao.RequestRegistry.lookup;

@Repository
public class EventLogDaoImpl implements EventLogDao {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final DataSource ds;

    protected NamedParameterJdbcTemplate jdbc;

    @Autowired
    public EventLogDaoImpl(DataSource ds) {
        this.ds = ds;
    }

    @PostConstruct
    public void init() {
        jdbc = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public void store(EventLog eventLog) {
        jdbc.update(lookup("eventlog/CreateEventLog"), prepareParams(eventLog));

    }

    private Map<String, Object> prepareParams(EventLog model) {
        Map<String, Object> params = new HashMap<>();

        params.put("eventlog_id", UUID.randomUUID().toString().substring(25));
        params.put("eventlogtype_id", model.getType().getId());
        params.put("operation", model.getOperation());
        params.put("request", toJsonString(model.getRequest()));
        params.put("response", toJsonString(model.getResponse()));
        params.put("commentary", model.getCommentary());

        params.put("date", Timestamp.from(ZonedDateTime.now().toInstant()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            params.put("username", authentication.getName());
            params.put("ip", details.getRemoteAddress());
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            String ip = request.getRemoteAddr();
            params.put("username", null);
            params.put("ip", ip);
        }

        return params;
    }

    private String toJsonString(Object model) {
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
