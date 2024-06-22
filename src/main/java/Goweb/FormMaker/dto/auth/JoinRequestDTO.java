package Goweb.FormMaker.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class JoinRequestDTO {
    @NotEmpty(message = "studentNumber은 필수입니다.")
    private Long studentNumber;

    @NotEmpty(message = "studentNumber은 필수입니다.")
    private String name;

    @NotEmpty(message = "password는 필수입니다.")
    private String password;

}