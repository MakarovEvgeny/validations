package project.service;

import project.model.Change;

import java.util.List;

public interface ChangesAware {

    List<Change> getChanges(String modelId);

}
