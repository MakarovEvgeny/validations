package project.dao.tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import project.dao.BaseVersionableModelDao;
import project.dao.ConcurrentModificationException;
import project.dao.FindAbility;
import project.dao.SearchParamsProcessor;
import project.model.Change;
import project.model.query.SearchParams;
import project.model.tag.Tag;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.springframework.dao.support.DataAccessUtils.requiredSingleResult;
import static project.dao.RequestRegistry.lookup;
import static project.dao.SearchParamsProcessor.process;

@Repository
public class TagDao extends BaseVersionableModelDao<Tag> implements FindAbility<Tag> {

    public static final RowMapper<Tag> TAG_MAPPER =
            (rs, rowNum) -> new Tag(rs.getString("id"), rs.getString("name"), rs.getInt("version"),
                    rs.getString("commentary"), rs.getString("description"));

    private final static String TAG_ID_COLUMN = "tag_id";

    public TagDao(DataSource ds) {
        super(ds);
    }

    @Override
    public List<Change> getChanges(String id) {
        return jdbc.query(lookup("tag/LoadChanges"), singletonMap("id", id), changeMapper);
    }

    @Override
    public Tag loadVersion(int versionId) {
        return jdbc.queryForObject(lookup("tag/LoadTagVersion"), singletonMap("id", versionId), TAG_MAPPER);
    }

    @Override
    public Tag load(String tagId) {
        return requiredSingleResult(load(singletonList(tagId)));
    }

    public List<Tag> load(List<String> tagIds) {
        if (tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return jdbc.query(lookup("tag/LoadTag"), singletonMap("ids", tagIds), TAG_MAPPER);
    }

    @Override
    public void create(Tag tag) {
        GeneratedKeyHolder idHolder = new GeneratedKeyHolder();
        jdbc.update(lookup("tag/CreateTag"), new MapSqlParameterSource(prepareParams(tag)), idHolder,
                new String[] {TAG_ID_COLUMN});
        createHistory(idHolder.getKeys().get(TAG_ID_COLUMN).toString(), tag);
    }

    @Override
    public void update(Tag tag) {
        int affectedRows = jdbc.update(lookup("tag/UpdateTag"), prepareParams(tag));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(tag);
    }

    @Override
    public void remove(Tag tag) {
        int affectedRows = jdbc.update(lookup("tag/DeleteTag"), prepareParams(tag));
        if (affectedRows == 0) {
            throw new ConcurrentModificationException();
        }
        createHistory(tag);
    }

    public void remove(List<Tag> tags) {
        if (tags.isEmpty()) {
            return;
        }

        List<SqlParameterSource> params = tags.stream()
                .map(this::prepareParams)
                .map(MapSqlParameterSource::new)
                .collect(Collectors.toList());

        int[] affectedRows = jdbc.batchUpdate(lookup("tag/DeleteTag"), params.toArray(new SqlParameterSource[]{}));
        if (Arrays.stream(affectedRows).sum() < tags.size()) {
            throw new ConcurrentModificationException();
        }

        //TODO убрать цикл
        for (Tag tag : tags) {
            createHistory(tag);
        }
    }

    @Override
    public List<Tag> find(SearchParams searchParams) {
        SearchParamsProcessor.ProcessResult result = process(lookup("tag/FindTag"), searchParams);
        return jdbc.query(result.getResultQuery(), result.getParams(), TAG_MAPPER);
    }

    private void createHistory(Tag tag) {
        createHistory(tag.getId(), tag);
    }

    private void createHistory(String tagId, Tag tag) {
        Map<String, Object> params = prepareHistoricalParams(tag);
        params.put("id", tagId);
        jdbc.update(lookup("tag/CreateTagHistory"), params);
    }

    @Override
    protected Map<String, Object> prepareParams(Tag tag) {
        Map<String, Object> params = super.prepareParams(tag);
        params.put("name", tag.getName());
        params.put("description", tag.getDescription());
        return params;
    }
}
