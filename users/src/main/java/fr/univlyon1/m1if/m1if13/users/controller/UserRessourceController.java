package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.Set;

/**
 * Controller des opérations CRUD.
 */
@Controller
public class UserRessourceController {

    @Autowired
    private UserDao userDao;

    /**
     * TEST DE GET.
     */
    @ResponseBody
    @GetMapping(value = "/test", produces = {"text/html"})
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userDao.getAll());
        mav.setViewName("users");
        return mav;
    }

    /**
     * Retourne tout les utilisateurs sous forme d'un ensemble de login.
     * @return un ensemble de login
     */
    @ResponseBody
    @GetMapping(value = "/users", produces = {"application/json", "application/xml"})
    public Set<String> getAllUsers() {
        return userDao.getAll();
    }

    /**
     * Retourne un utilisateur.
     * @return un ensemble de login
     */
    @ResponseBody
    @GetMapping(value = "/users/{login}", produces = {"application/json", "application/xml"})
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
}
