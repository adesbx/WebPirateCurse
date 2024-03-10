package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserRessourceControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDao userDao;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userDao.save(new User("Bob", Species.PIRATE, "1234"));
    }

    /**
     * Test du get users en json
     * on vérifie qu'il renvoit bien du json
     * @throws Exception
     */
    @Test
    void getUsersJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Test du get users en xml
     * on vérifie qu'il renvoit bien du xml
     * @throws Exception
     */
    @Test
    void getUsersXML() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));
    }

    /**
     * Test du get users en html
     * on vérifie qu'il renvoit bien du html
     * @throws Exception
     */
    @Test
    void getUsersHTML() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    /**
     * Test du get un user en json
     * on vérifie qu'il renvoit bien du json
     * @throws Exception
     */
    @Test
    void getOneUserJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "Bob" )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Test du get un user en xml
     * on vérifie qu'il renvoit bien du xml
     * @throws Exception
     */
    @Test
    void getOneUserXML() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "Bob" )
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));
    }

    /**
     * Test du get un user en html
     * on vérifie qu'il renvoit bien du html
     * @throws Exception
     */
    @Test
    void getOneUserHTML() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "Bob" )
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    /**
     * Test get un user qui n'existe pas
     * devrait renvoyer not found
     * @throws Exception
     */
    @Test
    void getOneUserNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "no_one")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isNotFound());
    }

    /**
     * Test post créer un User avec du json
     * on vérifie le content type en json
     * @throws Exception
     */
    @Test
    void postCreateUserJson() throws Exception {
        String jsonData = "{\"login\": \"Bobi\", \"species\": \"PIRATE\", \"password\": \"1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test post créer un User avec du urlencoded
     * on vérifie le content type en urlencoded
     * @throws Exception
     */
    @Test
    void postCreateUserUrlEncoded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("login", "Bobi")
                        .param("species", "PIRATE")
                        .param("password", "1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test post créer avec un paramètre en moins
     * devrait faire une erreur bad request
     * @throws Exception
     */
    @Test
    void postCreateUserMissParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users" )
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("login", "Bob")
                        .param("password", "1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test modifier un user en utilisant du json
     * @throws Exception
     */
    @Test
    void putUserJson() throws Exception {
        String jsonData = "{\"species\": \"VILLAGEOIS\", \"password\": \"1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{login}", "Bob")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test modifier un user en utilisant du urlencoded
     * @throws Exception
     */
    @Test
    void putUserUrlEncoded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{login}", "Bob")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("species", "VILLAGEOIS")
                        .param("password", "1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test modifier un user avec un seul paramètre
     * la requete devrait passer
     * @throws Exception
     */
    @Test
    void putUserUrlEncodedOneParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{login}", "Bob")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("species", "VILLAGEOIS")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test modifier un user qui n'existe pas
     * devrait retourner une erreur Not Found
     * @throws Exception
     */
    @Test
    void putUserNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{login}", "no_one")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("species", "VILLAGEOIS")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test delete un user
     * @throws Exception
     */
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{login}", "Bob")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test delete un user
     * devrait retourner une erreur Not Found
     * @throws Exception
     */
    @Test
    void deleteUserNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{login}", "no_one")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
