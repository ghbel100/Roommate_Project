package example.roommate.Service;





import example.roommate.Data.Room.Room;
import example.roommate.Data.Synchronizer.RoomKeyResponse;
import example.roommate.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }
    public List<String> getAllRoomsNames() {

        return roomRepository.getRoomsNames();
    }

    public Room getRoomById(Long id) {

        return roomRepository.findById(id).orElse(null);
    }

    public void addRoom(Room room) {

        roomRepository.save(room);
    }
    //For Testing purpose
    public Long addRoomAndReturnID(Room room) {

        Room saved = roomRepository.save(room);
        return saved.getRoomId();
    }

    public void updateRoom(Room room) {

        roomRepository.save(room);
    }

    public void deleteRoom(Room room) {

        roomRepository.delete(room);
    }

    public Optional<Room> findByUUID(UUID id){
       return roomRepository.findById(id);
    }


    public void deleteRoombyId(Long roomId) {
        roomRepository.delete(getRoomById(roomId));
    }

    public  List<RoomKeyResponse> getAllRoomsWithKeys() {
        return roomRepository.findRoomsAndKeys();
    }
}