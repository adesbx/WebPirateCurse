package fr.univlyon1.m1if.m1if13.users;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserRessourceControllerTests {
//    User anny, francois;
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//
//    @BeforeEach
//    void setUp() {
//        anny = new User("Anny Bonney", Species.PIRATE, "milsabor");
//        francois = new User("François Perrin", Species.VILLAGEOIS, "ChaussureNoire");
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void testGetUsersJSON() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users")
//                        .param("param1", "value1")
//                        .param("param2", "value2")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.result").value("success"));
//    }
}
