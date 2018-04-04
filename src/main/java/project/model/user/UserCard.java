package project.model.user;

/**
 * Данные пользователя для выдачи на клиент.
 */
public class UserCard {
    private String username;

    public UserCard(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
