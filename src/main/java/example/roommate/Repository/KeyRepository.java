package example.roommate.Repository;

import example.roommate.Data.Room.Key;
import example.roommate.Data.Room.Room;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeyRepository extends CrudRepository<Key,Long> {
    @Query("select * from key where key.id = :id")
    Optional<Key> findById(@Param("id") UUID id);
}
