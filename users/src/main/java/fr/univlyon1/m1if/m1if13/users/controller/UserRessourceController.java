package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.dto.UserDto;
import fr.univlyon1.m1if.m1if13.users.dto.UserModifyDto;
import fr.univlyon1.m1if.m1if13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

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
    @Operation(summary = "Get all users in json/xml/html format",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful operation", content = {
                            @Content(mediaType = "application/json"),
                            @Content(mediaType = "application/xml"),
                            @Content(mediaType = "text/html")
                    })
            })
    public Set<String> getAllUsers() {
        return userDao.getAll();
    }

    /**
     * Retourne un utilisateur.
     * @param login login de l'utilisateur
     * @return une vue
     */
    @ResponseBody
    @CrossOrigin(origins = {"https://192.168.75.36", "http://localhost:5173", "http://localhost:8080", "http://localhost:3376",
            "http://192.168.75.36:8080",
            "https://192.168.75.36:8443", "http://192.168.75.36:3376", "https://192.168.75.36:3376",
            "https://192.168.75.36/game", "https://192.168.75.36/api", "https://192.168.75.36/secret"})
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
     * @param login login de l'utilisateur
     * @return un ensemble de login
     */
    @ResponseBody
    @CrossOrigin(origins = {"https://192.168.75.36", "http://localhost:5173", "http://localhost:8080", "http://localhost:3376",
            "http://192.168.75.36:8080",
            "https://192.168.75.36:8443", "http://192.168.75.36:3376", "https://192.168.75.36:3376",
            "https://192.168.75.36/game", "https://192.168.75.36/api", "https://192.168.75.36/secret"})
    @GetMapping(value = "/users/{login}", produces = {"application/json", "application/xml"})
    @Operation(summary = "Get one user in json/xml/html format",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful operation", content = {
                            @Content(mediaType = "application/json"),
                            @Content(mediaType = "application/xml"),
                            @Content(mediaType = "text/html")
                    }),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request", content = @Content())
            })
    public User getUsers(@PathVariable final String login) {
        return userDao.get(login).orElseThrow(() ->
                new NoSuchElementException("user " + login + " doesn't exist..."));
    }

    /**"
     * Crée un utilisateur.
     * @param userDto utilisateur qu'il faut créer
     */
    @ResponseBody
    @PostMapping(value = "/users", consumes = {"application/json"})
    @Operation(summary = "Create a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    public void createUser(@RequestBody final UserDto userDto) throws Exception {
        if (userDto.getLogin() == null || userDto.getSpecies() == null
                || userDto.getPassword() == null) {
            throw new Exception("Il manque un paramètre");
        }
        userDao.save(new User(userDto.getLogin(), userDto.getSpecies(), userDto.getPassword()));
    }

    /**
     * Crée un utilisateur.
     * @param userDto l'utilisateur
     */
    @ResponseBody
    @PostMapping(value = "/users", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(summary = "Create a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    public void createUserURL(@ModelAttribute final UserDto userDto) throws Exception {
        if (userDto.getLogin() == null || userDto.getSpecies() == null
                || userDto.getPassword() == null) {
            throw new Exception("Il manque un paramètre");
        }
        userDao.save(new User(userDto.getLogin(), userDto.getSpecies(), userDto.getPassword()));
    }

    /**
     * Maj d'un utilisateur.
     * @param login login de l'utilisateur
     * @param userDto l'utilisateur
     */
    @CrossOrigin(origins = {"https://192.168.75.36", "http://localhost:5173", "http://localhost:8080", "http://localhost:3376",
            "http://192.168.75.36:8080",
            "https://192.168.75.36:8443", "http://192.168.75.36:3376", "https://192.168.75.36:3376",
            "https://192.168.75.36/game", "https://192.168.75.36/api", "https://192.168.75.36/secret"})
    @ResponseBody
    @PutMapping(value = "/users/{login}", consumes =  {"application/json"})
    @Operation(summary = "Modify a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void modifyUser(@PathVariable final String login,
                           @RequestBody final UserModifyDto userDto) {
        String[] tab = new String[3];
        if (userDto.getSpecies() != null) {
            tab[0] = String.valueOf(userDto.getSpecies());
        } else {
            tab[0] = null;
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            System.out.println(userDto.getPassword());
            tab[1] = userDto.getPassword();
        } else {
            tab[1] = null;
        }
        if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
            System.out.println(userDto.getImage());
            tab[2] = userDto.getImage();
        } else {
            tab[2] = null;
        }
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            userDao.update(user.get(), tab);
        } else {
            throw new NoSuchElementException("Utilisateur introuvable");
        }
    }

    /**
     * Maj d'un utilisateur.
     * @param login login de l'utilisateur
     * @param userDto l'utilisateur
     */
    @CrossOrigin(origins = {"https://192.168.75.36", "http://localhost:5173", "http://localhost:8080", "http://localhost:3376",
            "http://192.168.75.36:8080",
            "https://192.168.75.36:8443", "http://192.168.75.36:3376", "https://192.168.75.36:3376",
            "https://192.168.75.36/game", "https://192.168.75.36/api", "https://192.168.75.36/secret"})
    @ResponseBody
    @PutMapping(value = "/users/{login}", consumes =  {
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(summary = "Modify a user",
            tags = "Operation REST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    public void modifyUserURL(@PathVariable final String login,
                              @ModelAttribute final UserModifyDto userDto) {
        String[] tab = new String[3];
        if (userDto.getSpecies() != null) {
            tab[0] = String.valueOf(userDto.getSpecies());
        } else {
            tab[0] = null;
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            System.out.println(userDto.getPassword());
            tab[1] = userDto.getPassword();
        } else {
            tab[1] = null;
        }
        if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
            System.out.println(userDto.getImage());
            tab[2] = userDto.getImage();
        } else {
            tab[2] = null;
        }
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            userDao.update(user.get(), tab);
        } else {
            throw new NoSuchElementException("Utilisateur introuvable");
        }
    }

    /**
     * Delete un utilisateur.
     * @param login de l'utilisateur
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
