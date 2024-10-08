package fr.univlyon1.m1if.m1if13.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO with the data we get from the server for login.
 */
public class UserLoginDto {
    private String login;
    private String password;

    /**
     * Basic constructor.
     */
    public UserLoginDto() {
    }

    /**
     * Constructor with the param we need.
     * @param login
     * @param password
     */
    @JsonCreator
    public UserLoginDto(@JsonProperty("login") final String login,
                   @JsonProperty("password") final String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
