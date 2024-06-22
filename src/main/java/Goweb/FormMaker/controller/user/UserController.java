package Goweb.FormMaker.controller.user;

import Goweb.FormMaker.dto.findPasswordDTO;
import Goweb.FormMaker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auths")
public class UserController {

    private UserService userService;

    @PostMapping("find/password")
    public ResponseEntity<String> findPassword(findPasswordDTO findPasswordDTO){
        String password = userService.findPassword(findPasswordDTO);
        return new ResponseEntity<>(password,HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(findPasswordDTO findPasswordDTO){
        String password = userService.findPassword(findPasswordDTO);
        return new ResponseEntity<>(password,HttpStatus.OK);
    }

    @PostMapping("join")
    public ResponseEntity<String> joinUser(findPasswordDTO findPasswordDTO){
        String password = userService.findPassword(findPasswordDTO);
        return new ResponseEntity<>(password,HttpStatus.OK);
    }
}
