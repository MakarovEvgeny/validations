package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dao.tag.TagDao;
import project.dao.validation.ValidationDao;
import project.model.Change;
import project.model.query.SearchParams;
import project.model.tag.MergeTag;
import project.model.tag.Tag;
import project.model.validation.Validation;
import project.model.validation.ValidationDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ValidationServiceImpl implements ValidationService {

    private final ValidationDao dao;

    private final TagDao tagDao;

    @Autowired
    public ValidationServiceImpl(ValidationDao dao, TagDao tagDao) {
        this.dao = dao;
        this.tagDao = tagDao;
    }

    @Override
    public Validation load(String validationId) {
        return dao.load(validationId);
    }

    @Override
    public void create(Validation validation) {
        dao.create(validation);
    }

    @Override
    public void update(Validation validation) {
        dao.update(validation);
    }

    @Override
    public void remove(Validation validation) {
        dao.remove(validation);
    }

    @Override
    public List<ValidationDto> find(SearchParams searchParams) {
        return dao.find(searchParams);
    }

    @Override
    public List<Change> getChanges(String validationId) {
        return dao.getChanges(validationId);
    }

    @Override
    public Validation loadVersion(int versionId) {
        return dao.loadVersion(versionId);
    }

    @Override
    public void mergeTags(MergeTag mergeTag) {
        List<String> validationIds = dao.loadValidationIdsByTagIds(mergeTag.getMergeTagIds());
        List<Tag> mergeTags = tagDao.load(mergeTag.getMergeTagIds());

        Tag mainTag = tagDao.load(mergeTag.getTagId());
        if (!validationIds.isEmpty()) {
            List<Validation> validations = dao.load(validationIds);

            for (Validation validation : validations) {
                validation.mergeTags(mainTag, mergeTags);
                validation.setCommentary("Объединяем теги " + String.join(", ", mergeTags.stream().map(Tag::getName).collect(Collectors.toList()))
                        + " с " + mainTag.getName());
            }

            dao.update(validations);
        }

        for (Tag tag : mergeTags) {
            tag.setCommentary("Тег удален из-за объединения с " + mainTag.getName());
        }
        tagDao.remove(mergeTags);
    }
}
