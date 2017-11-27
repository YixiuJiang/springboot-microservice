package spaceshuttle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spaceshuttle.model.APIResponse;
import spaceshuttle.model.User;
import spaceshuttle.repository.UserRepository;

import java.util.HashSet;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:h2-db.properties")
public class UserControllerTest {

    private static final String USERS_URI_WITH_ID = "/users/{id}";
    private static final String USERS_URI = "/users";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserById() throws Exception {
        User user = addTestUser();
        APIResponse apiResponse = APIResponseMother.getDefaultAPIResponse();
        apiResponse.setResponseObject(user);
        user.setRoles(new HashSet<>());
        mvc.perform(MockMvcRequestBuilders.get(USERS_URI_WITH_ID, user.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(apiResponse))));
        cleanUpUsers();
    }

    @Test
    public void testAddUser() throws Exception {
        User user = UserMother.getDefaultUser();
        User savedUser = new User();
        BeanUtils.copyProperties(user, savedUser);
        APIResponse apiResponse = APIResponseMother.getDefaultAPIResponse();
        apiResponse.setResponseObject(savedUser);
//        savedUser.setId(2L);
        mvc.perform(MockMvcRequestBuilders.post(USERS_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(apiResponse))));
        cleanUpUsers();
    }


    @Test
    public void testDeleteUser() throws Exception {
        User user = addTestUser();
        APIResponse apiResponse = APIResponseMother.getDefaultAPIResponse();
        mvc.perform(MockMvcRequestBuilders.delete(USERS_URI_WITH_ID, user.getId()).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(apiResponse))));
    }

    @Test
    public void testEditUser() throws Exception {
        User user = addTestUser();
        user.setUsername("newUserName");
        APIResponse apiResponse = APIResponseMother.getDefaultAPIResponse();
        apiResponse.setResponseObject(user);
        user.setRoles(new HashSet<>());
        mvc.perform(MockMvcRequestBuilders.put(USERS_URI_WITH_ID, user.getId()).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(apiResponse))));
        cleanUpUsers();
    }

    private User addTestUser() {
        User user = UserMother.getDefaultUser();
        return userRepository.save(user);
    }

    private void cleanUpUsers() {
        userRepository.deleteAll();
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