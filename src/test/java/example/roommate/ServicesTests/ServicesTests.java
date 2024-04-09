package example.roommate.ServicesTests;

import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import example.roommate.Data.Room.Room;
import example.roommate.Repository.ArbeitsplatzRepository;
import example.roommate.Repository.RoomRepository;
import example.roommate.Service.ArbeitsplatzService;
import example.roommate.Service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class ServicesTests {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ArbeitsplatzRepository arbeitsplatzRepository;

    RoomService roomService;
    ArbeitsplatzService arbeitsplatzService;

    @BeforeEach
    void setUp() {
        roomService = new RoomService(roomRepository);
        arbeitsplatzService = new ArbeitsplatzService(arbeitsplatzRepository);
    }


    private Room createRoom(String roomName) {
        Room room = new Room(roomName);
        roomService.addRoom(room);
        return room;
    }

    private Long createAndAddRoom(String roomName) {
        Room room = new Room(roomName);
        return roomService.addRoomAndReturnID(room);
    }

    @Test
    @DisplayName("Der roomService kann Raum hinzufügen")
    void testAddSingleRoom() {
        // Arrange
        Room room = createRoom("neben Café");

        // Act
        List<Room> allRooms = roomService.getAllRooms();

        // Assert
        assertThat(allRooms).contains(room);
    }

    @Test
    @DisplayName("Der roomService kann mehrere Räume hinzufügen")
    void testAddMultipleRooms() {
        // Arrange
        Room room1 = createRoom("neben Café");
        Room room2 = createRoom("neben Mensa");

        // Act
        List<Room> allRooms = roomService.getAllRooms();

        // Assert
        assertThat(allRooms).contains(room1, room2);
    }

    @Test
    @DisplayName("Der ArbeitsplatzService kann Arbeitsplatz hinzufügen")
    void addSingleArbeitsplatzTest() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz("TestArbeitsplatz", room1Id);

        // Act
        arbeitsplatzService.addPlatz(arbeitsplatz);
        List<Arbeitsplatz> allArbeitsplaetze = arbeitsplatzService.getAllArbeitsplaetze();

        // Assert
        assertThat(allArbeitsplaetze).contains(arbeitsplatz);
    }

    @Test
    @DisplayName("Der ArbeitsplatzService kann mehrere Arbeitsplätze hinzufügen")
    void addMultipleArbeitsplaetzeTest() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Long room2Id = createAndAddRoom("neben Mensa");
        Arbeitsplatz arbeitsplatz1 = new Arbeitsplatz("TestArbeitsplatz1", room1Id);
        Arbeitsplatz arbeitsplatz2 = new Arbeitsplatz("TestArbeitsplatz2", room2Id);

        // Act
        arbeitsplatzService.addPlatz(arbeitsplatz1);
        arbeitsplatzService.addPlatz(arbeitsplatz2);
        List<Arbeitsplatz> allArbeitsplaetze = arbeitsplatzService.getAllArbeitsplaetze();

        // Assert
        assertThat(allArbeitsplaetze).contains(arbeitsplatz1, arbeitsplatz2);
    }

    @Test
    @DisplayName("Test getAllRoomsNames")
    void testGetAllRoomsNames() {
        // Arrange
        createRoom("neben Café");
        createRoom("neben Mensa");

        // Act
        List<String> allRoomNames = roomService.getAllRoomsNames();

        // Assert
        assertThat(allRoomNames).contains("neben Café", "neben Mensa");
    }

    @Test
    @DisplayName("Test getAllArbeitsplaetzeNames")
    void testGetAllArbeitsplaetzeNames() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Long room2Id = createAndAddRoom("neben Mensa");
        Arbeitsplatz arbeitsplatz1 = new Arbeitsplatz("TestArbeitsplatz1", room1Id);
        Arbeitsplatz arbeitsplatz2 = new Arbeitsplatz("TestArbeitsplatz2", room2Id);
        arbeitsplatzService.addPlatz(arbeitsplatz1);
        arbeitsplatzService.addPlatz(arbeitsplatz2);

        // Act
        List<String> allArbeitsplaetzeNames = arbeitsplatzService.getAllArbeitsplaetzeNames();

        // Assert
        assertThat(allArbeitsplaetzeNames).contains("TestArbeitsplatz1", "TestArbeitsplatz2");
    }

    @Test
    @DisplayName("Test getRoomById")
    void testGetRoomById() {
        // Arrange
        Long roomId = createAndAddRoom("neben Café");

        // Act
        Room room = roomService.getRoomById(roomId);

        // Assert
        assertThat(room.getName()).isEqualTo("neben Café");
    }

    @Test
    @DisplayName("Test getArbeitsplatzById")
    void testGetArbeitsplatzById() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz("TestArbeitsplatz", room1Id);
        Long arbeitsplatzId = arbeitsplatzService.addPlatzAndReturnID(arbeitsplatz);

        // Act
        Arbeitsplatz retrievedArbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);

        // Assert
        assertThat(retrievedArbeitsplatz.getName()).isEqualTo("TestArbeitsplatz");
    }

    @Test
    @DisplayName("Test updateRoom")
    void testUpdateRoom() {
        // Arrange
        Long roomId = createAndAddRoom("neben Café");
        Room room = roomService.getRoomById(roomId);
        room.setName("neben Mensa");

        // Act
        roomService.updateRoom(room);
        Room updatedRoom = roomService.getRoomById(roomId);

        // Assert
        assertThat(updatedRoom.getName()).isEqualTo("neben Mensa");
    }

    @Test
    @DisplayName("Test deleteRoom")
    void testDeleteRoom() {
        // Arrange
        Long roomId = createAndAddRoom("neben Café");
        Room room = roomService.getRoomById(roomId);

        // Act
        roomService.deleteRoom(room);
        List<Room> allRooms = roomService.getAllRooms();

        // Assert
        assertThat(allRooms).doesNotContain(room);
    }
    @Test
    @DisplayName("Test deleteRoomById")
    void testDeleteRoombById() {
        // Arrange
        Long roomId = createAndAddRoom("neben Café");
        Room room = roomService.getRoomById(roomId);

        // Act
        roomService.deleteRoombyId(roomId);
        List<Room> allRooms = roomService.getAllRooms();

        // Assert
        assertThat(allRooms).doesNotContain(room);
    }

    @Test
    @DisplayName("Test updateArbeitsplatz")
    void testUpdateArbeitsplatz() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz("TestArbeitsplatz", room1Id);
        Long arbeitsplatzId = arbeitsplatzService.addPlatzAndReturnID(arbeitsplatz);
        Arbeitsplatz retrievedArbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);
        retrievedArbeitsplatz.setName("UpdatedArbeitsplatz");

        // Act
        arbeitsplatzService.updateArbeitsplatz(retrievedArbeitsplatz);
        Arbeitsplatz updatedArbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);

        // Assert
        assertThat(updatedArbeitsplatz.getName()).isEqualTo("UpdatedArbeitsplatz");
    }

    @Test
    @DisplayName("Test addRoomAndReturnID")
    void testAddRoomAndReturnID() {
        // Arrange & Act
        Long roomId = createAndAddRoom("neben Café");

        // Assert
        assertThat(roomId).isNotNull();
    }

    @Test
    @DisplayName("Test deleteArbeitsplatz")
    void testDeleteArbeitsplatz() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz("TestArbeitsplatz", room1Id);
        Long arbeitsplatzId = arbeitsplatzService.addPlatzAndReturnID(arbeitsplatz);
        Arbeitsplatz retrievedArbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);

        // Act
        arbeitsplatzService.deleteArbeitsplatz(retrievedArbeitsplatz);
        List<Arbeitsplatz> allArbeitsplaetze = arbeitsplatzService.getAllArbeitsplaetze();

        // Assert
        assertThat(allArbeitsplaetze).doesNotContain(retrievedArbeitsplatz);
    }
    @Test
    @DisplayName("Test deleteAllArbeitsplatzeByRoomId")
    void testDeleteAllArbeitsplatzeByRoomId() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz1 = new Arbeitsplatz("TestArbeitsplatz1", room1Id);
        Arbeitsplatz arbeitsplatz2 = new Arbeitsplatz("TestArbeitsplatz2", room1Id);
        arbeitsplatzService.addPlatz(arbeitsplatz1);
        arbeitsplatzService.addPlatz(arbeitsplatz2);
        // Act
        arbeitsplatzService.deleteAllArbeitsplatzeByRoomId(room1Id);
        List<Arbeitsplatz> allArbeitsplaetze = arbeitsplatzService.getAllArbeitsplaetze();
        // Assert
        assertThat(allArbeitsplaetze).isEmpty();
    }

    @Test
    @DisplayName("Test  allArbeitsplatzeInRaumMitId")
    void testAllArbeitsplatzeInRaumMitId() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz1 = new Arbeitsplatz("TestArbeitsplatz1", room1Id);
        Arbeitsplatz arbeitsplatz2 = new Arbeitsplatz("TestArbeitsplatz2", room1Id);
        arbeitsplatzService.addPlatz(arbeitsplatz1);
        arbeitsplatzService.addPlatz(arbeitsplatz2);
        // Act
        List<Arbeitsplatz> allArbeitsplatzeInRaum = arbeitsplatzService.allArbeitsplatzeInRaumMitId(room1Id);
        // Assert
        assertThat(allArbeitsplatzeInRaum).contains(arbeitsplatz1, arbeitsplatz2);
    }
    @Test
    @DisplayName("Test  deleteArbeitsplatzById")
    void testDeleteArbeitsplatzById() {
        // Arrange
        Long room1Id = createAndAddRoom("neben Café");
        Arbeitsplatz arbeitsplatz = new Arbeitsplatz("TestArbeitsplatz", room1Id);
        Long arbeitsplatzId = arbeitsplatzService.addPlatzAndReturnID(arbeitsplatz);

        // Act
        arbeitsplatzService.deleteArbeitsplatzById(arbeitsplatzId);
        Arbeitsplatz deletedArbeitsplatz = arbeitsplatzService.getArbeitsplatzById(arbeitsplatzId);
        // Assert
        assertThat(deletedArbeitsplatz).isNull();
    }


}