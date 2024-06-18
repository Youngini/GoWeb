package Goweb.FormMaker.service.user;

import Goweb.FormMaker.DTO.findPasswordDTO;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String findPassword(findPasswordDTO findPasswordDTO){
        User user = userRepository.findUserByStudentNumberAndName(findPasswordDTO.getStudentNumber(), findPasswordDTO.getName());
        return user.getPassword();
    }
}
