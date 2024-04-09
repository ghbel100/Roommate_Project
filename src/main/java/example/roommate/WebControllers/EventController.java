package example.roommate.WebControllers;

import example.roommate.Data.Room.Key;
import example.roommate.Data.Room.Room;
import example.roommate.Data.Synchronizer.RoomKeyResponse;
import example.roommate.Service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class EventController {
    private final RoomService roomService;

    public EventController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping("/api/access")
    public  List <RoomKeyResponse> verfuegbareRaueme() {
        return roomService.getAllRoomsWithKeys();
    }



}
