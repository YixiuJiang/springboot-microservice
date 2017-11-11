package spaceshuttle;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import spaceshuttle.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:h2-db.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;


    @Before
    public void setMvc(){
        User user = new User();
        user.setPassword("123");
        user.setId(1L);
        user.setUsername("Frank");



    }


    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Ignore
    @Test
    public void testGetUserById() throws Exception{

        mvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"flag\":true,\"respondCode\":null,\"respondContent\":null,\"respondObject\":null}")));
    }

    @Test
    public void testUpdateUserById() throws Exception {
        User user = new User();
        user.setPassword("123");
        user.setUsername("Frank");

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"flag\":true,\"respondCode\":null,\"respondContent\":null,\"respondObject\":{\"id\":1,\"username\":\"Frank\",\"password\":\"123\"}}")));
    }

    /*
        Copy From sample
        PaserToJosn
     */

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