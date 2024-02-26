package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;
import java.util.Set;


/**
 * Controller des opérations sur users.
 */
@Controller
public class UsersOperationsController {

    @Autowired
    private UserDao userDao;

    /**
     * Retourne tout les utilisateurs sous forme d'un ensemble de login.
     * @return un ensemble de login
     */
    @ResponseBody
    @GetMapping(value = "/users", produces = {"application/json"})
    public Set<String> getAllUsers() {
        return userDao.getAll();
    }

    /**
     * Retourne un utilisateur.
     * @return un ensemble de login
     */
    @ResponseBody
    @GetMapping(value = "/users/{login}", produces = {"application/json"})
    public Optional<User> getUsers(@PathVariable final String login) {
        return userDao.get(login);
    }

    /**
     * Crée un utilisateur.
     * @param newUser utilisateur qu'il faut crée
     */
    @ResponseBody
    @PostMapping(value = "/users", produces = {"application/json"})
    public void createUser(@RequestBody final User newUser) {
        userDao.save(newUser);
    }

    /**
     * Maj d'un utilisateur.
     * @param login login de l'utilisateur
     * @param species nouvelle species
     * @param password nouveau mot de passe
     */
    @ResponseBody
    @PutMapping(value = "/users/{login}", produces = {"application/json"})
    public void modifyUser(@PathVariable final String login,
                           @RequestParam("species") final Species species,
                           @RequestParam("password") final String password) {
        String[] tab = new String[2];
        tab[0] = String.valueOf(species);
        tab[1] = password;
        Optional<User> user = userDao.get(login);
        userDao.update(user.get(), tab);
    }

    /**
     * Delete un utilisateur.
     */
    @ResponseBody
    @DeleteMapping(value = "/users/{login}", produces = {"application/json"})
    public void deleteUser(@PathVariable final String login) {
        Optional<User> user = userDao.get(login);
        userDao.delete(user.get());
    }


    /**
     * Procédure de login utilisée par un utilisateur.
     * @param login Le login de l'utilisateur. L'utilisateur doit avoir été créé
     *             préalablement et son login doit être présent dans le DAO.
     * @param password Le password à vérifier.
     * @return Une ResponseEntity avec le JWT dans le header "Authentication" si le
     * login s'est bien passé, et le code de statut approprié (204, 401 ou 404).
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam("login") final String login,
                                      @RequestParam("password") final String password,
                                      @RequestHeader("Origin") final String origin) {
        // TODO
        return null;
    }

//    /**
//     * Réalise la déconnexion
//     */
//    @PostMapping("/logout")
    // TODO

    /**
     * Méthode destinée au serveur Node pour valider l'authentification d'un utilisateur.
     * @param jwt Le token JWT qui se trouve dans le header "Authentication" de la requête
     * @param origin L'origine de la requête (pour la comparer avec celle du client,
     *               stockée dans le token JWT)
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     */
    @GetMapping("/authenticate")
    public ResponseEntity<Void> authenticate(@RequestParam("jwt") final String jwt,
                                             @RequestParam("origin") final String origin) {
        // TODO
        return null;
    }
}
