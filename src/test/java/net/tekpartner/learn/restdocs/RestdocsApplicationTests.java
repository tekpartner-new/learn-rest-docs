package net.tekpartner.learn.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.tekpartner.learn.restdocs.repository.BookRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestdocsApplicationTests {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)).build();
    }

    @Test
    public void booksListExample() throws Exception {

        this.mockMvc.perform(get("/api/books").contextPath("/api"))
                .andExpect(status().isOk())
                .andDo(document("books-list-example",
                        links(
                                linkWithRel("self").description("Canonical link for this resource"),
                                linkWithRel("profile").description("The ALPS profile for this resource"))));
    }

    @Test
    public void booksCreateExample() throws Exception {
        Map<String, Object> book = new HashMap<String, Object>();
        book.put("title", "REST maturity model");
        book.put("description", "http://martinfowler.com/articles/richardsonMaturityModel.html");

        this.mockMvc.perform(
                post("/api/books").contextPath("/api").contentType(MediaTypes.HAL_JSON).content(
                        this.objectMapper.writeValueAsString(book))).andExpect(
                status().isCreated())
                .andDo(document("books-create-example",
                        requestFields(
                                fieldWithPath("title").description("The title of the book"),
                                fieldWithPath("description").description("The description of the book"))));
    }

    @Test
    public void bookGetExample() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1)
                .contextPath("/api")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("book-get-example",
                        responseFields(
                                fieldWithPath("_links.self.href").description("The Self HREF of the book"),
                                fieldWithPath("_links.book.href").description("The Book HREF of the book"),
                                fieldWithPath("title").description("The title of the book"),
                                fieldWithPath("description").description("The description of the book"))));
    }
}
