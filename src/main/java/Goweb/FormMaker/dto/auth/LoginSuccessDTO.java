package Goweb.FormMaker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginSuccessDTO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
}