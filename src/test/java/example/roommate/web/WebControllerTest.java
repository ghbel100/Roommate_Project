package example.roommate.web;


import example.roommate.Security.MethodSecurityConfiguration;
import example.roommate.Security.WebSecurityConfiguration;
import example.roommate.Service.*;
import example.roommate.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.SessionAttributes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest
@Import({WebSecurityConfiguration.class, MethodSecurityConfiguration.class})
@SessionAttributes("roomId")
public class WebControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    RoomService roomService;
    @MockBean
    ArbeitsplatzService arbeitsplatzService;
    @MockBean
    AusstatungService ausstatungService;
    @MockBean
    ReservationService reservationService;
    @MockBean
    PersonService personService;
    @Test
    @DisplayName("Status beim Starten der Webanwendung ohne authentifizierung ist 302")
    void chooseRoomWithoutLogin() throws Exception {
        mvc.perform(get("/chooseroom"))
                .andExpect(status().is(302));
    }
    @Test
    void test1() throws Exception {
        mvc.perform(get("/chooseroom"))
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github"));
    }
    @Test
    @WithMockOAuth2User(login = "Jens")
    @DisplayName("Status beim Starten der Webanwendung ist OK")
    void test2() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }
    @Test
    @WithMockOAuth2User(login="Jens", roles = {"USER"})
    void AdminWithoutRole() throws Exception {
        mvc.perform(get("/addroom"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockOAuth2User(login="Jens", roles = {"USER", "ADMIN"})
    void AdminWithRole() throws Exception {
        mvc.perform(get("/addroom"))
                .andExpect(status().isOk());
    }





}