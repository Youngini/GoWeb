package Goweb.FormMaker.repository.user;

import Goweb.FormMaker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByStudentNumberAndName(Long studentNumber, String name);
}
