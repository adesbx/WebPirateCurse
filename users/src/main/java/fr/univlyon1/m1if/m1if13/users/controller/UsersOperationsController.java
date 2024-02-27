package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static fr.univlyon1.m1if.m1if13.users.utils.JwtHelper.generateToken;
import static fr.univlyon1.m1if.m1if13.users.utils.JwtHelper.verifyToken;
import static fr.univlyon1.m1if.m1if13.users.utils.JwtHelper.noLifeTimeToken;

/**
 * Controller des opérations sur users.
 */
@Controller
public class UsersOperationsController {

    @Autowired
    private UserDao userDao;

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
                                      @RequestHeader("Origin") final String origin)
            throws AuthenticationException {
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            user.get().authenticate(password);
            if (user.get().isConnected()) {
                String token = generateToken(login, origin);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authentication", "Bearer " + token);
                return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Réalise la déconnexion.
     * @param jwt Le token JWT qui se trouve dans le header "Authentication" de la requête
     * @param origin L'origine de la requête (pour la comparer avec celle du client,
     * stockée dans le token JWT)
     *@return Une réponse vide avec un code de statut approprié (204, 400, 401).
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authentication") final String jwt,
                                       @RequestHeader("origin") final String origin) {
        String token = jwt.replace("Bearer ", "");
        String login = verifyToken(token, origin);
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            if (user.get().isConnected()) {
                user.get().disconnect();
                String newToken = noLifeTimeToken(login, origin);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authentication", "Bearer " + newToken);
                return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


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
        String token = jwt.replace("Bearer ", "");
        String login = verifyToken(token, origin);
        Optional<User> user = userDao.get(login);
        if (user.isPresent()) {
            if (user.get().isConnected()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
