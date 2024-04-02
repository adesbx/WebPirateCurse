package fr.univlyon1.m1if.m1if13.users.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.validation.constraints.NotNull;

import javax.naming.AuthenticationException;
import java.util.Date;

/**
 * Classe qui centralise les opérations de validation et de génération
 * d'un token "métier", c'est-à-dire dédié à cette application.
 *
 * @author Lionel Médini
 */
public final class JwtHelper {
    private static final String SECRET = "monsecret2024";
    private static final String ISSUER = "MIF13 2024";
    private static final long LIFETIME = 1800000; // Durée de vie d'un token : 30 minutes
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    /**
     * Pour valider la règle <code>HideUtilityClassConstructorCheck</code> de CheckStyle...
     * Une classe utilitaire qui n'a que des méthodes statiques ne doit pas avoir de
     * constructeur public,
     * parce qu'il ne servirait à rien de l'instancier.
     */
    private JwtHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Vérifie l'authentification d'un utilisateur grâce à un token JWT.
     *
     * @param token le token à vérifier
     * @param req   la requête HTTP (nécessaire pour vérifier si l'origine de la
     *              requête est la même que celle du token
     * @return un booléen qui indique si le token est bien formé et valide (pas expiré) et
     * si l'utilisateur est authentifié
     */
    public static String verifyToken(final String token, @NotNull final String req)
            throws NullPointerException, JWTVerificationException,
            Exception, AuthenticationException {
        try {
            JWTVerifier authenticationVerifier = JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .withAudience(req) // Non-reusable verifier instance
                    .build();
            authenticationVerifier.verify(token); // Lève une NullPointerException si le token
            // n'existe pas et une JWTVerificationException s'il est invalide
            DecodedJWT jwt = JWT.decode(token); // Pourrait lever une JWTDecodeException mais
            // comme le token est vérifié avant, cela ne devrait pas arriver
            return jwt.getClaim("sub").asString();
        } catch (JWTDecodeException e) {
            throw new Exception("Token invalide");
        } catch (TokenExpiredException e) {
            throw new AuthenticationException("le Token est expirer");
        }
    }


    /**
     * Crée un token avec les caractéristiques de l'utilisateur.
     *
     * @param subject le login de l'utilisateur
     * @param req     la requête HTTP pour pouvoir en extraire l'origine avec getOrigin()
     * @return le token signé
     * @throws JWTCreationException si les paramètres ne permettent pas de créer un token
     */
    public static String generateToken(final String subject, final String req)
            throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withAudience(req)
                .withExpiresAt(new Date(new Date().getTime() + LIFETIME))
                .sign(ALGORITHM);
    }

    /**
     * Crée un token avec une lifetime de 0.
     *
     * @param subject le login de l'utilisateur
     * @param req     la requête HTTP pour pouvoir en extraire l'origine avec getOrigin()
     * @return le token signé
     * @throws JWTCreationException si les paramètres ne permettent pas de créer un token
     */
    public static String noLifeTimeToken(final String subject, final String req)
            throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withAudience(req)
                .withExpiresAt(new Date(new Date().getTime()))
                .sign(ALGORITHM);
    }
}

