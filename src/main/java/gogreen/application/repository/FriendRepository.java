package gogreen.application.repository;

import gogreen.application.model.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findByFusername(String fusername);

    List<Friend> findByFriend(String friend);
}
