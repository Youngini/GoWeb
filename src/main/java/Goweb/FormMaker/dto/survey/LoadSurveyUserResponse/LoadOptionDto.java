package Goweb.FormMaker.dto.survey.LoadSurveyUserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadOptionDto {

    private Long optionId;
    private String name;
    private Integer num;
    private String imageUrl;
}
