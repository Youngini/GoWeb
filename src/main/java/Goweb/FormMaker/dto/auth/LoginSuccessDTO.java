package Goweb.FormMaker.dto.auth;

import Goweb.FormMaker.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginSuccessDTO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private UserType userType;
}