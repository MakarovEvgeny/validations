package project.model.user;

public class RegisterUserDto {

    private String username;

    private String password;

    private String password2;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String username, String password, String password2) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }
}
