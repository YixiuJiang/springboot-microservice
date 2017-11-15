package spaceshuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceshuttle.model.Instructor;

interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
