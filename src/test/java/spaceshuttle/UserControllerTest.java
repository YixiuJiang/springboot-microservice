package spaceshuttle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spaceshuttle.controller.UserController;
import spaceshuttle.model.User;
import spaceshuttle.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:h2-db.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController subject;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    @Test
    public void testAddNewUser() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        mvc.perform(MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Test
    public void testGetAllUsers() throws Exception {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        given(userRepository.findAll()).willReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.responseObject.username", is("user1")))
                .andExpect(jsonPath("$.responseObject.password", is("password")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setUsername("old_user");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(user);

        user.setUsername("new_user");
        user.setPassword("new_password");

        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put("/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.responseObject.username", is("new_user")))
                .andExpect(jsonPath("$.responseObject.password", is("new_password")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setUsername("delete_user");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(user);
        doNothing().when(userRepository).delete(new Long(1));

        mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.responseObject.username", is("delete_user")))
                .andExpect(jsonPath("$.responseObject.password", is("password")));
    }


}