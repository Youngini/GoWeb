package Goweb.FormMaker.service.user;

import Goweb.FormMaker.dto.findPasswordDTO;
import Goweb.FormMaker.DTO.auth.JoinRequestDTO;
import Goweb.FormMaker.DTO.auth.LoginSuccessDTO;
import Goweb.FormMaker.DTO.auth.findPasswordDTO;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.exception.AppException;
import Goweb.FormMaker.exception.error.AppErrorCode;
import Goweb.FormMaker.exception.error.AuthErrorCode;
import Goweb.FormMaker.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import Goweb.FormMaker.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

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
    public LoginSuccessDTO join(JoinRequestDTO userJoinRequestDTO) {
        checkDuplicateStudentNumber(userJoinRequestDTO.getStudentNumber());
        User user = User.createFromJoin(userJoinRequestDTO, encoder);
        userRepository.save(user);
        return JwtUtil.createTokens(user);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateStudentNumber(Long studentNumber) {
        userRepository.findByStudentNumber(studentNumber).ifPresent(user -> {
            throw new AppException(AuthErrorCode.STUDENT_NUMBER_DUPLICATED);
        });
    }

    @Transactional(readOnly = true)
    public LoginSuccessDTO login(Long studentNumber, String password) {
        User user = findUserByStudentNumber(studentNumber);
        comparePassword(password, user.getPassword());
        return JwtUtil.createTokens(user);
    }

    private void comparePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new AppException(AppErrorCode.INVALID_USER);
        }
    }

    private User findUserByStudentNumber(Long studentNumber) {
        return userRepository.findUserByStudentNumber(studentNumber);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 user를 찾을 수 없습니다."));
    }
}
