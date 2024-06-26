package Goweb.FormMaker.repository.user;

import Goweb.FormMaker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByStudentNumberAndName(Long studentNumber, String name);

    User findUserByStudentNumber(Long studentNumber);

    Optional<User> findByStudentNumber(Long studentNumber);
}
