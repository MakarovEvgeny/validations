package project.model.tag;

import java.util.List;
import java.util.Objects;

/**
 * Информация необходимая для объелинения нескольких тегов в один.
 */
public class MergeTag {

    /** id основного тега, который заменит удаляемые. */
    private String tagId;

    /** id тегов, которые нужно объединить с основным. */
    private List<String> mergeTagIds;

    public String getTagId() {
        return tagId;
    }

    public List<String> getMergeTagIds() {
        return mergeTagIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeTag mergeTag = (MergeTag) o;
        return Objects.equals(tagId, mergeTag.tagId) && Objects.equals(mergeTagIds, mergeTag.mergeTagIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, mergeTagIds);
    }
}
