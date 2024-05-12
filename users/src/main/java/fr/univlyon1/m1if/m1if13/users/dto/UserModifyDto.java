package fr.univlyon1.m1if.m1if13.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.univlyon1.m1if.m1if13.users.model.Species;

/**
 * DTO with the data we get from the server for modification of user.
 */
public class UserModifyDto {
    private Species species;
    private String password;
    private String image;

    /**
     * Basic constructor.
     */
    public UserModifyDto() {
    }

    /**
     * Constructor with the param we need.
     * @param species
     * @param password
     */
    @JsonCreator
    public UserModifyDto(@JsonProperty("species") final Species species,
                         @JsonProperty("password") final String password,
                         @JsonProperty("image") final String image) {
        this.species = species;
        this.password = password;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
