package fr.univlyon1.m1if.m1if13.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.univlyon1.m1if.m1if13.users.model.Species;

/**
 * DTO with the data we get from the server for creation of user.
 */
public class UserDto {
        private String login;
        private Species species;
        private String password;

    /**
     * Basic constructor.
     */
    public UserDto() {
    }

    /**
     * Constructor with the param we need.
     * @param login
     * @param species
     * @param password
     */
    @JsonCreator
    public UserDto(@JsonProperty("login") final String login,
                   @JsonProperty("species") final Species species,
                   @JsonProperty("password") final String password) {
        this.login = login;
        this.species = species;
        this.password = password;
    }

        public String getLogin() {
        return login;
    }

        public void setLogin(final String login) {
        this.login = login;
    }

        public Species getSpecies() {
        return species;
    }

        public void setSpecies(final Species species) {
        this.species = species;
    }

        public String getPassword() {
        return password;
    }

        public void setPassword(final String password) {
        this.password = password;
    }

}
