package Goweb.FormMaker.service.user;

import Goweb.FormMaker.DTO.findPasswordDTO;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUserNow(){
        return new User();
        //코드 입력
    }

    @Transactional
    public String findPassword(findPasswordDTO findPasswordDTO){
        User user = userRepository.findUserByStudentNumberAndName(findPasswordDTO.getStudentNumber(), findPasswordDTO.getName());
        return user.getPassword();
    }

    @Transactional
    public void join(){
        //코드 입력
    }

    @Transactional
    public void login(){
        //코드 입력
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 user를 찾을 수 없습니다."));
    }
}
