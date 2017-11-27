package spaceshuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceshuttle.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
