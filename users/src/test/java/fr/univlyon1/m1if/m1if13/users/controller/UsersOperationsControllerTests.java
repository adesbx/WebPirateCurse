package fr.univlyon1.m1if.m1if13.users.controller;

import fr.univlyon1.m1if.m1if13.users.dao.UserDao;
import fr.univlyon1.m1if.m1if13.users.model.Species;
import fr.univlyon1.m1if.m1if13.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UsersOperationsControllerTests {
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

    @Test
    void postLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("login", "Bob")
                        .param("password", "1234")
                        .header("Origin", "http://localhost")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Authentication"));
    }

    @Test
    void postLoginMissingParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("login", "Bob")
                        .header("Origin", "http://localhost")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postLoginBadPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("login", "Bob")
                        .param("password", "not")
                        .header("Origin", "http://localhost")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postLoginBadLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("login", "no_one")
                        .param("password", "1234")
                        .header("Origin", "http://localhost")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void postLogout() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("login", "Bob")
                        .param("password", "1234")
                        .header("Origin", "http://localhost")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Authentication"))
                .andReturn().getResponse();

        mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authentication", response.getHeader("Authentication"))
                        .header("Origin", "http://localhost"))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Authentication"));
    }

}
