package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dao.tag.TagDao;
import project.model.Change;
import project.model.query.SearchParams;
import project.model.tag.Tag;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Change> getChanges(String modelId) {
        return tagDao.getChanges(modelId);
    }

    @Override
    public List<Tag> find(SearchParams searchParams) {
        return tagDao.find(searchParams);
    }

    @Override
    public Tag loadVersion(int versionId) {
        return tagDao.loadVersion(versionId);
    }

    @Override
    public Tag load(String modelId) {
        return tagDao.load(modelId);
    }

    @Override
    public void create(Tag model) {
        tagDao.create(model);
    }

    @Override
    public void update(Tag model) {
        tagDao.update(model);
    }

    @Override
    public void remove(Tag model) {
        tagDao.remove(model);
    }
}
