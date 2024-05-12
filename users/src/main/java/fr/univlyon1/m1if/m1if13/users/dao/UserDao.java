package fr.univlyon1.m1if.m1if13.users.dao;

import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Dao sur User.
 */
@Component
public class UserDao implements Dao<User> {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Constructeur pour créer deux utilisateurs.
     * //TODO A supprimer plus tard ?
     */
    public UserDao() {
        users.put("John", new User("John", Species.PIRATE, "1234"));
        users.put("Susan", new User("Susan", Species.VILLAGEOIS, "5678"));
        users.put("titi", new User("titi", Species.PIRATE, "abcd"));
        users.put("toto", new User("toto", Species.VILLAGEOIS, "efgh"));
        users.put("ADMIN", new User("ADMIN", Species.ADMIN, "ADMIN"));
    }

    @Override
    public Optional<User> get(final String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Set<String> getAll() {
        return this.users.keySet();
    }

    @Override
    public void save(final User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public void update(final User user, final String[] params) {
        if (params[0] != null && params[1] != null && params[2] != null) {
            user.setSpecies(Species.valueOf(params[0]));
            user.setPassword(params[1]);
            user.setImage(params[2]);
        } else if (params[0] != null && params[1] != null) {
            user.setSpecies(Species.valueOf(params[0]));
            user.setPassword(params[1]);
        } else if (params[0] != null && params[2] != null) {
            user.setSpecies(Species.valueOf(params[0]));
            user.setImage(params[2]);
        } else if (params[1] != null && params[2] != null) {
            user.setPassword(params[1]);
            user.setImage(params[2]);
        } else if (params[0] != null) {
            user.setSpecies(Species.valueOf(params[0]));
        } else if (params[1] != null) {
            user.setPassword(params[1]);
        } else if (params[2] != null) {
            user.setImage(params[2]);
        }
        users.put(user.getLogin(), user);
    }

    @Override
    public void delete(final User user) {
        users.remove(user.getLogin(), user);
    }
}
