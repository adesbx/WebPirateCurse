package fr.univlyon1.m1if.m1if13.users.dao;

import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;

import java.util.*;

public class UserDao implements Dao<User> {

    private Map<String ,User> users = new HashMap<>();

    public UserDao() {
        users.put("John", new User("John", Species.PIRATE,"1234"));
        users.put("Susan",new User("Susan", Species.VILLAGEOIS, "5678"));
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Set<String> getAll() {
        return this.users.keySet();
    }

    @Override
    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public void update(User user, String[] params) {
//        if(params[0] != null && params[1] != null) {
//            users.put(params[0], new User(params[0], user.getSpecies(), params[1]));
//        }
//        else if (params[0] != null && params[1] == null) {
//            users.put(params[0], new User(params[0], user.getSpecies(), ));
//        } else if (params[0] == null && params[1] != null) {
//            users.put(user.getLogin(), new User(user.getLogin(), user.getSpecies(), params[1]));
//        }
    }
}
