package Goweb.FormMaker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class findPasswordDTO {

    private Long studentNumber;
    private String name;
}
