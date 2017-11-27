package spaceshuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceshuttle.model.Kid;
import spaceshuttle.model.KidPK;

public interface KidRepository extends JpaRepository<Kid, KidPK> {
}
