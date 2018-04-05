package project.service;

import project.model.user.UserCard;

public interface UserService {

    void register(String username, String password);

    UserCard whoAmI();
}