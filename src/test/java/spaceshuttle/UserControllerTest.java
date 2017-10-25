package spaceshuttle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spaceshuttle.model.User;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:h2-db.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Test
    public void testGetUserById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":null}")));

    }

    @Test
    public void testCrudUser() throws Exception {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":{\"id\":1,\"username\":\"username\",\"password\":\"password\"}}")));

        //test getting user by id
        mvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8)

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":{\"id\":1,\"username\":\"username\",\"password\":\"password\"}}")));

        //test updating user by id
        user.setPassword("newPassword");
        user.setUsername("newUsername");
        mvc.perform(MockMvcRequestBuilders.put("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":null}")));

        //check updated user
        mvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":{\"id\":1,\"username\":\"newUsername\",\"password\":\"newPassword\"}}")));

        //test deleting user by id
        mvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8)

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"success\":true,\"errorCode\":null,\"errorMessage\":null,\"responseObject\":null}")));

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}