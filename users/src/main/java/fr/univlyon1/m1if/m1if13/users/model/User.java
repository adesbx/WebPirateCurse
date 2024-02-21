package fr.univlyon1.m1if.m1if13.users.model;

import javax.naming.AuthenticationException;

/**
 * User.
 */
public class User {
    private final String login;
    private Species species;
    private String password;
    // Permet d'invalider une connexion même si le token est toujours valide
    private boolean connected = false;
    // Nom du fichier image qui représentera l'utilisateur sur la carte
    private String image;

    /**
     * Constructeur User.
     * @param login Login de l'utilisateur
     * @param species Species de l'utilisateur
     * @param password Password de l'utilisateur
     */
    public User(final String login, final Species species, final String password) {
        this.login = login;
        this.species = species;
        this.password = password;
    }

    /**
     * Constructeur User.
     * @param login Login de l'utilisateur
     * @param species Species de l'utilisateur
     * @param password Password de l'utilisateur
     * @param image Image de l'utilisateur
     */
    public User(final String login, final Species species,
                final String password, final String image) {
        this.login = login;
        this.species = species;
        this.password = password;
        this.image = image;
    }

    public String getLogin() {
        return login;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(final Species species) {
        this.species = species;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Fonction pour s'authentifier.
     * @param password Password de l'utilisateur
     */
    public void authenticate(final String password) throws AuthenticationException {
        if (!password.equals(this.password)) {
            throw new AuthenticationException("Erroneous password");
        }
        this.connected = true;
    }

    /**
     * Fonction pour se déconnecter.
     */
    public void disconnect() {
        this.connected = false;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }
}
