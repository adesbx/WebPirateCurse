package fr.univlyon1.m1if.m1if13.users;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {

    UserDao userDao = new UserDao();

    @Test
    void getUser() {
        User user = userDao.get("John").get();
        assertEquals(user.getLogin(), "John");
    }

    @Test
    void getUserNull() {
        Optional<User> user = userDao.get("Xx");
        assertEquals(user, Optional.empty());
    }

    @Test
    void getAllUser() {
        Integer taille = userDao.getAll().toArray().length;
        assertEquals(taille, 2);
    }

    @Test
    void addUser() {
        Integer taille = userDao.getAll().toArray().length;
        userDao.save(new User("Columbo", Species.PIRATE, "JeanLuc"));
        Integer tailleV2 = userDao.getAll().toArray().length;
        assertEquals(tailleV2, taille + 1);
    }

    @Test
    void modifyUserPassword() throws AuthenticationException {
        User jl = new User("Columbo", Species.PIRATE, "JeanLuc");
        userDao.save(jl);
        String[] params =  new String[2];
        params[1] = "1234";
        userDao.update(jl, params);
        User user = userDao.get("Columbo").get();
        user.authenticate("1234");
        assert(user.isConnected());
    }

    @Test
    void modifyUserSpecies() {
        User jl = new User("Columbo", Species.PIRATE, "JeanLuc");
        userDao.save(jl);
        String[] params =  new String[2];
        params[0] = "VILLAGEOIS";
        userDao.update(jl, params);
        User user = userDao.get("Columbo").get();
        assertEquals(user.getSpecies(), Species.VILLAGEOIS);
    }

    @Test
    void deleteUser() {
        userDao.save(new User("Columbo", Species.PIRATE, "JeanLuc"));
        Integer taille = userDao.getAll().toArray().length;
        userDao.delete(userDao.get("Columbo").get());
        Integer tailleV2 = userDao.getAll().toArray().length;
        assertEquals(tailleV2, taille - 1);
    }
}
