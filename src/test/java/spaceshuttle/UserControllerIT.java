package spaceshuttle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import spaceshuttle.model.APIResponse;
import spaceshuttle.model.User;
import spaceshuttle.repository.UserRepository;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:h2-db.properties")
public class UserControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository repository;

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");

        User user1 = new User();
        User user2 = new User();

        user1.setUsername("user_one");
        user1.setPassword("password");

        user2.setUsername("user_two");
        user2.setPassword("password");

        repository.deleteAll();
        repository.save(user1);
        repository.save(user2);

        mapper = new ObjectMapper();

    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/users/hello",
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
    }

    @Test
    public void testAddNewUser() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        ResponseEntity<APIResponse> responseEntity = template.postForEntity(base.toString() + "/users", user,
                APIResponse.class);

        String expected = "{\"username\":\"username\",\"password\":\"password\"}";

        APIResponse response = responseEntity.getBody();

        String actual = mapper.writeValueAsString(response.getResponseObject());

        assertEquals(actual, expected);

    }

    @Test
    public void testGetAllUsers() {
        ResponseEntity<String> responseEntity = template.getForEntity(base.toString() + "/users/all",
                String.class);

        String expected = "[{\"username\":\"user_one\",\"password\":\"password\"}," +
                "{\"username\":\"user_two\",\"password\":\"password\"}]";
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), expected);
    }

    @Test
    public void testGetUserById() {

        ResponseEntity<APIResponse> responseEntity = template.getForEntity(base.toString() + "/users/{id}",
                APIResponse.class, 1);

        APIResponse response = responseEntity.getBody();

        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setUsername("user_edited");
        user.setPassword("password");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<APIResponse> responseEntity = template.exchange(base.toString() + "/users/{id}",
                HttpMethod.PUT, entity, APIResponse.class, 2);

        APIResponse response = responseEntity.getBody();

        String expected = "{\"username\":\"user_edited\",\"password\":\"password\"}";

        String actual = mapper.writeValueAsString(response.getResponseObject());

        assertEquals(response.isSuccess(), true);
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteUser() throws Exception {

        ResponseEntity<APIResponse> responseEntity = template.exchange(base.toString() + "/users/{id}",
                HttpMethod.DELETE, null, APIResponse.class, 1);

        APIResponse response = responseEntity.getBody();

        String expected = "{\"username\":\"user_one\",\"password\":\"password\"}";

        String actual = mapper.writeValueAsString(response.getResponseObject());

        assertEquals(response.isSuccess(), true);
        assertEquals(expected, actual);
    }
}