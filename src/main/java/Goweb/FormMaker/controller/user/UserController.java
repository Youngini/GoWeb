package Goweb.FormMaker.controller.user;

import Goweb.FormMaker.dto.auth.*;
import Goweb.FormMaker.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class UserController {

    private final UserService userService;

    @PostMapping("/find/password")
    public ResponseEntity<String> findPassword(findPasswordDTO findPasswordDTO){
        String password = userService.findPassword(findPasswordDTO);
        return new ResponseEntity<>(password,HttpStatus.OK);
    }

    @PostMapping("/login")
    public LoginSuccessDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        System.out.println(loginRequestDTO.toString());
        return userService.login(loginRequestDTO.getStudentNumber(), loginRequestDTO.getPassword());
    }

    @PostMapping("/join")
    public LoginSuccessDTO join(@RequestBody @Valid JoinRequestDTO joinRequestDTO) {
        System.out.println(joinRequestDTO.toString());
        return userService.join(joinRequestDTO);
    }

    @PostMapping("/adjoin")
    public LoginSuccessDTO adminjoin(@RequestBody @Valid JoinAdminRequestDTO joinAdminRequestDTO) {
        System.out.println(joinAdminRequestDTO.toString());
        return userService.adjoin(joinAdminRequestDTO);
    }


}
