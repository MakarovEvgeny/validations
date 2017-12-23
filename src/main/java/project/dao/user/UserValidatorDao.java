package project.dao.user;

public interface UserValidatorDao {

    boolean usernameAlreadyExists(String username);

}
