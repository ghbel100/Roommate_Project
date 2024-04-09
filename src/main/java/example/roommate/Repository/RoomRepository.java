package example.roommate.Repository;

import example.roommate.Data.Room.Room;
import example.roommate.Data.Synchronizer.RoomKeyResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Override
    List<Room> findAll();
    @Query("select room.name from room")
    List<String> getRoomsNames();

   @Query("select * from room where room.id = :id")
    Optional<Room> findById(@Param("id") UUID id);

   @Query("select k.id as key ,r.id as room,r.name as raum, k.owner as owner FROM room r INNER JOIN key k ON r.room_id = k.key_id")
    List<RoomKeyResponse> findRoomsAndKeys();
}
