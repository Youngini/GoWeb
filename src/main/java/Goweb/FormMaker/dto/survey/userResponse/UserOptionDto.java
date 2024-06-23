package Goweb.FormMaker.dto.survey.userResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOptionDto {

    private Long optionId;
    private String name;
    private Integer num;
    private String imageUrl;
}
