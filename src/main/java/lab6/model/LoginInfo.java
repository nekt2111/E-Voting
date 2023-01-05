package lab6.model;

import lombok.Data;

@Data
public final class LoginInfo {
    public final String login;
    public final String password;

    public LoginInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
