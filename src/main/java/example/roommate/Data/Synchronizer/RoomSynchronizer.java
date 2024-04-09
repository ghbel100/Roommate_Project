package example.roommate.Data.Synchronizer;

import example.roommate.Data.Room.Room;
import example.roommate.Service.RoomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class RoomSynchronizer {

    private RoomService roomService;

    public RoomSynchronizer(RoomService roomService) {
        this.roomService = roomService;
    }

    @Scheduled(fixedDelay = 10000)
    public void fetchEvents() {
        List<Room> rooms = WebClient.create()
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .scheme("http")
                                .host("localhost")
                                .port(3000)
                                .path("room")
                                .build()
                )
                .retrieve()
                .bodyToFlux(Room.class)
                .collectList()
                .block(Duration.of(8, ChronoUnit.SECONDS));
        for (Room room : rooms) {
            processEvent(room);
        }
    }

    private void processEvent(Room room) {
        if(roomService.findByUUID(room.getId()).isEmpty()){
            roomService.addRoom(room);
        }
    }
}