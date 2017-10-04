package project.service;

import project.model.Entity;

import java.util.List;

public interface EntityService {

    Entity load(String entityId);

    void create(Entity entity);

    void update(Entity entity);

    void remove(Entity entity);

    List<Entity> find();

}
