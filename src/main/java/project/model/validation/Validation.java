package project.model.validation;

import project.model.BaseVersionableModel;
import project.model.message.Message;
import project.model.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Проверка aka Валидация.
 */
public class Validation extends BaseVersionableModel {

    /** Тип проверки. */
    private Severity severity;

    /** {@link project.model.message.Message} */
    private Message message;

    /** Описание проверки, бизнес требования. */
    private String description;

    /** Коллекция уникальных {@link project.model.entity.Entity} */
    private Set<ValidationEntity> validationEntities = new HashSet<>();

    /** Коллекция уникальных {@link project.model.tag.Tag} */
    private Set<Tag> tags = new HashSet<>();

    public Validation() {
        //for spring
    }

    public Validation(String id, Severity severity, Message message, String description, int version, String commentary) {
        super(id, version, commentary);
        this.severity = severity;
        this.message = message;
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Message getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public Set<ValidationEntity> getValidationEntities() {
        return validationEntities;
    }

    public void setValidationEntities(Set<ValidationEntity> validationEntities) {
        this.validationEntities = validationEntities;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Validation that = (Validation) o;
        return severity == that.severity &&
                Objects.equals(message, that.message) &&
                Objects.equals(description, that.description) &&
                Objects.equals(validationEntities, that.validationEntities) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), severity, message, description, validationEntities, tags);
    }

    public void mergeTags(Tag mainTag, List<Tag> mergeTags) {
        if (tags.removeAll(mergeTags)) {
            tags.add(mainTag);
        }
    }
}
