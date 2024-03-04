package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.NoSuchElementException;
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
     * Retourne tout les utilisateurs sous forme d'un ensemble de login.
     * @return une vue
     */
    @ResponseBody
    @GetMapping(value = "/users", produces = {"text/html"})
    public ModelAndView getAllUsersHTML() {
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
    @Operation(summary = "Get all users in json/xml format",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public Set<String> getAllUsers() {
        return userDao.getAll();
    }

    /**
     * Retourne un utilisateur.
     * @return une vue
     */
    @ResponseBody
    @GetMapping(value = "/users/{login}", produces = {"text/html"})
    public ModelAndView getUsersHTML(@PathVariable final String login) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", userDao.get(login).orElseThrow(() ->
                new NoSuchElementException("user " + login + " doesn't exist...")));
        mav.setViewName("user");
        return mav;
    }

    /**
     * Retourne un utilisateur.
     * @return un ensemble de login
     */
    @ResponseBody
    @GetMapping(value = "/users/{login}", produces = {"application/json", "application/xml"})
    @Operation(summary = "Get one user in json/xml format",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public User getUsers(@PathVariable final String login) throws Exception {
        return userDao.get(login).orElseThrow(() ->
                new NoSuchElementException("user " + login + " doesn't exist..."));
    }

    /**"
     * Crée un utilisateur.
     * @param newUser utilisateur qu'il faut créer
     */
    @ResponseBody
    @PostMapping(value = "/users", consumes = {"application/json"})
    @Operation(summary = "Create a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void createUser(@RequestBody final User newUser) {
        // newuser déjà présent ?
        // sinn a til tt les params ?
        userDao.save(newUser);
    }

    /**
     * Crée un utilisateur.
     */
    @ResponseBody
    @PostMapping(value = "/users", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(summary = "Create a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void createUserURL(@RequestParam("login") final String login,
                              @RequestParam("species") final Species species,
                              @RequestParam("password") final String password)
            throws BadRequestException {
        if (login == null || species == null || password == null) {
            throw new BadRequestException("Il manque un paramètre");
        }
        User newUser = new User(login, species, password);
        userDao.save(newUser);
    }

    /**
     * Maj d'un utilisateur.
     * @param login login de l'utilisateur
     */
    @ResponseBody
    @PutMapping(value = "/users/{login}", consumes =  {"application/json"})
    @Operation(summary = "Modify a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void modifyUser(@PathVariable final String login,
                           @RequestBody final Map<String, Object> requestParams) {
        String species = (String) requestParams.get("species");
        String password = (String) requestParams.get("password");
        String[] tab = new String[2];
        tab[0] = species;
        tab[1] = password;
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            userDao.update(user.get(), tab);
        } else {
            throw new NoSuchElementException("Utilisateur introuvable");
        }
    }

    /**
     * methode for login.
     * @param login
     * @param species
     * @param password
     */
    @ResponseBody
    @PutMapping(value = "/users/{login}", consumes =  {
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(summary = "Modify a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void modifyUserURL(@PathVariable final String login,
                           @RequestParam(value = "species", required = false) final String species,
                           @RequestParam(value = "password", required = false)
                           final String password) {
        String[] tab = new String[2];
        tab[0] = species;
        tab[1] = password;
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            userDao.update(user.get(), tab);
        } else {
            throw new NoSuchElementException("Utilisateur introuvable");
        }
    }

    /**
     * Delete un utilisateur.
     */
    @ResponseBody
    @DeleteMapping(value = "/users/{login}")
    @Operation(summary = "Delete a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void deleteUser(@PathVariable final String login) {
        Optional<User> user = userDao.get(login);
        userDao.delete(user.get());
    }
}
