package alexander.tsoy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void userAdd() throws Exception {
        mockMvc.perform(post("/people")
                .content("{  \"firstName\" : \"Noah\",  \"lastName\" : \"Lennox\", \"birthDate\" : \"17.07.1978\", " +
                        "\"email\" : \"pandabear@anco.com\", \"password\" : \"qwerty\" }" )
                .contentType(contentType))
                .andExpect(status().isCreated());
    }

    @Test
    public void userSearch() throws Exception {
        mockMvc.perform(post("/people")
                .content("{  \"firstName\" : \"Noah\",  \"lastName\" : \"Lennox\", \"birthDate\" : \"17.07.1978\", " +
                        "\"email\" : \"pandabear@anco.com\", \"password\" : \"qwerty\" }" )
                .contentType(contentType))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/people")
                .content("{  \"firstName\" : \"Noah\",  \"lastName\" : \"Lennox\", \"birthDate\" : \"17.07.1978\", " +
                        "\"email\" : \"pandabear@anco.com\", \"password\" : \"qwerty\" }" )
                .contentType(contentType))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/people/search/findByEmail?email=pandabear@anco.com")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void userDelete() throws Exception {
        MvcResult mvcResult= mockMvc.perform(post("/people")
                .content("{  \"firstName\" : \"David\",  \"lastName\" : \"Portner\", \"birthDate\" : \"24.04.1979\", " +
                        "\"email\" : \"aveytare@anco.com\", \"password\" : \"qwerty\" }" )).andReturn();
        System.out.println(mvcResult.getResponse().getHeader("Location"));
        mockMvc.perform(delete("/people/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/people/1")).andExpect(status().isNotFound());
    }

    @Test
    public void userIllegalDelete() throws Exception {
        mockMvc.perform(delete("/people/6")).andExpect(status().isNotFound());
    }
}