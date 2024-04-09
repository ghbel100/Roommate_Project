package example.roommate.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import example.roommate.Data.Arbeitsplatz.Ausstatung;
import example.roommate.Data.Room.Room;
import example.roommate.Service.ArbeitsplatzService;
import example.roommate.Service.AusstatungService;
import example.roommate.Service.RoomService;
import example.roommate.WebControllers.AdminController;
import example.roommate.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockOAuth2User
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RoomService roomService;
    @MockBean
    ArbeitsplatzService arbeitsplatzService;
    @MockBean
    AusstatungService  ausstatungService;

    @MockBean
    Room room;
    @MockBean
    Arbeitsplatz arbeitsplatz;
    @MockBean
    Ausstatung ausstatung;

    @Test
    @WithMockOAuth2User(login="something", roles = {"USER", "ADMIN"})
    void AdminWithRole() throws Exception {
        mvc.perform(get("/addroom"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Status beim Starten der Webanwendung ist OK")
    void test_1() throws Exception {
        mvc.perform(get("/addroom"))
                .andExpect(status().isOk())
                .andExpect(view().name("addraum"));
    }

    @Test
    @DisplayName("Post request for /addroom")
    void test2() throws Exception {
        when(roomService.addRoomAndReturnID(any())).thenReturn(1L);
        mvc.perform(post("/addroom")
                        .param("roomName", "zimmer1")
                        .param("deviceID", "1").with(csrf()))
                .andExpect(redirectedUrl("/addarbeitsplatz/1"));
    }

    @Test
    @DisplayName("Get request for /addarbeitsplatz/{roomId}")
    void test_3() throws Exception {
        mvc.perform(get("/addarbeitsplatz/{roomId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("addarbeitsplatz"));
    }

    @Test
    @DisplayName("Post request for /addarbeitsplatz/{roomId}")
    void test4() throws Exception {
        when(arbeitsplatzService.addPlatzAndReturnID(any())).thenReturn(2L);
        mvc.perform(post("/addarbeitsplatz/{roomId}", 1)
                        .param("arbeitsplatzName", "arbeitsplatz1").with(csrf()))
                .andExpect(redirectedUrl("/addaustattung/2"));
    }

    @Test
    @DisplayName("Get request for /addaustattung/{arbeitsplatzId}")
    void test_5() throws Exception {
        when(arbeitsplatzService.getArbeitsplatzById(any())).thenReturn(arbeitsplatz);
        mvc.perform(get("/addaustattung/{arbeitsplatzId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("addausstattung"));
    }

    @Test
    @DisplayName("Post request for /addaustattung/{arbeitsplatzId}")
    void test6() throws Exception {

        when(arbeitsplatzService.getArbeitsplatzById(any())).thenReturn(arbeitsplatz);
        mvc.perform(post("/addaustattung/{arbeitsplatzId}", 1L).with(csrf())
                        .param("austattungName", "austattung1"))

                .andExpect(redirectedUrl("/addaustattung/1"));
    }

    @Test
    @DisplayName("Get request for /addaustattung/{arbeitsplatzId}")
    void test_7() throws Exception {
        mvc.perform(get("/roomoverview"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomoverview"));
    }

    //    @Test
//    @DisplayName("Post request for /deleteroom/{roomId}")
//    void test7() throws Exception{
//        mvc.perform(post("/deleteroom/{roomId}",1L).with(csrf()))
//                .andExpect(redirectedUrl("/roomoverview"));
//          verify(arbeitsplatzService).deleteAllArbeitsplatzeByRoomId(1L);
//          verify(roomService).deleteRoombyId(1L);
//    }
    @Test
    @DisplayName("Get request for /modifyroom/{roomId}")
    void test_8() throws Exception {
        when(roomService.getRoomById(any())).thenReturn(room);
        when(arbeitsplatzService.allArbeitsplatzeInRaumMitId(1L)).thenReturn(Arrays.asList());
        mvc.perform(get("/modifyroom/{roomId}", 1L))
                .andExpect(view().name("arbeitsplatzeübersichtAdmin"));
    }

    @Test
    @DisplayName("Post Request for /room/{roomId}/platz/{arbeitsplatzId}")
    void test9() throws Exception {
        mvc.perform(post("/room/{roomId}/platz/{arbeitsplatzId}", 1L, 1L).with(csrf()))
                .andExpect(redirectedUrl("/modifyroom/1"));
        verify(arbeitsplatzService).deleteArbeitsplatzById(1L);
    }

    @Test
    @DisplayName("Get request for /modifyarbeitsplatz/{arbeitsplatzId}")
    void test_10() throws Exception {
        when(arbeitsplatzService.getArbeitsplatzById(any())).thenReturn(arbeitsplatz);
        when(arbeitsplatz.getAusstatungs()).thenReturn(Arrays.asList());
        mvc.perform(get("/modifyarbeitsplatz/{arbeitsplatzId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("ausstatungübersichtAdmin"));

        verify(arbeitsplatzService).getArbeitsplatzById(1L);
    }
    @Test
    @DisplayName("Post Request for /platz/{arbeitsplatzId}/ausstatung/{ausstatungName}")
    void test11() throws Exception {
       when(arbeitsplatzService.deleteAusstatung(any(),any())).thenReturn(arbeitsplatz);
        mvc.perform(post("/platz/{arbeitsplatzId}/ausstatung/{ausstatungName}", 1L,"A1").with(csrf()))
                .andExpect(redirectedUrl("/modifyarbeitsplatz/1"));
        verify(arbeitsplatzService).updateArbeitsplatz(arbeitsplatz);
    }
}




