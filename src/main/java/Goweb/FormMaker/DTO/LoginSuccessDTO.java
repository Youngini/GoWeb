package Goweb.FormMaker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginSuccessDTO {
    private String accessToken;
    private String refreshToken;
}