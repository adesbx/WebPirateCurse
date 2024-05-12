package fr.univlyon1.m1if.m1if13.users;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {

    UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        userDao.save(new User("Bob", Species.PIRATE, "1234"));
    }

    /**
     * Test si l'utilisateur est bien récupéré
     */
    @Test
    void getUser() {
        User user = userDao.get("Bob").get();
        assertEquals(user.getLogin(), "Bob");
    }

    /**
     * Test si l'utilisateur non existant est récupéré
     */
    @Test
    void getUserNull() {
        Optional<User> user = userDao.get("Xx");
        assertEquals(user, Optional.empty());
    }

    /**
     * Test pour récupérer tous les utilisateurs
     * Ici la taille devrait être de 4
     */
    @Test
    void getAllUser() {
        Integer taille = userDao.getAll().toArray().length;
        assertEquals(taille, 6);
    }

    /**
     * Test d'ajout d'un utilisateur
     * S'il s'est bien créé
     * getAll devrait donner taille 2
     */
    @Test
    void addUser() {
        Integer taille = userDao.getAll().toArray().length;
        userDao.save(new User("Columbo", Species.PIRATE, "JeanLuc"));
        Integer tailleV2 = userDao.getAll().toArray().length;
        assertEquals(tailleV2, taille + 1);
    }

    /**
     * Test modification mot de passe
     * On essaye ensuite de s'authentifier
     * avec le nouveau mdp
     */
    @Test
    void modifyUserPassword() throws AuthenticationException {
        User jl = new User("Columbo", Species.PIRATE, "JeanLuc");
        userDao.save(jl);
        String[] params =  new String[3];
        params[1] = "1234";
        userDao.update(jl, params);
        User user = userDao.get("Columbo").get();
        user.authenticate("1234");
        assert(user.isConnected());
    }

    /**
     * Test modification SPECIES
     * On essaye ensuite de récupérer la species
     * et voir si elle est bien à jour
     */
    @Test
    void modifyUserSpecies() {
        User jl = new User("Columbo", Species.PIRATE, "JeanLuc");
        userDao.save(jl);
        String[] params =  new String[3];
        params[0] = "VILLAGEOIS";
        userDao.update(jl, params);
        User user = userDao.get("Columbo").get();
        assertEquals(user.getSpecies(), Species.VILLAGEOIS);
    }

    /**
     * Test pour récupérer tous les utilisateurs
     * Ici la taille devrait être de taille - 1
     */
    @Test
    void deleteUser() {
        userDao.save(new User("Columbo", Species.PIRATE, "JeanLuc"));
        Integer taille = userDao.getAll().toArray().length;
        userDao.delete(userDao.get("Columbo").get());
        Integer tailleV2 = userDao.getAll().toArray().length;
        assertEquals(tailleV2, taille - 1);
    }
}