package fr.univlyon1.m1if.m1if13.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * UserApplication.
 */
@SpringBootApplication
@Import(WebConfig.class)
public class UsersApplication {

	/**
	 * fonction qui lance l'application.
	 * @param args arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
