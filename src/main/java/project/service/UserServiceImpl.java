package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import project.dao.user.UserDao;
import project.model.user.UserCard;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public void register(String username, String password) {
        dao.register(username, password);
    }

    @Override
    public UserCard whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = dao.loadUserByUsername(authentication.getName());
        return new UserCard(user.getUsername());
    }

}
