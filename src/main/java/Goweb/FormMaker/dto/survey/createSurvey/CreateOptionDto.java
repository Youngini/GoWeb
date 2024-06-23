package Goweb.FormMaker.dto.survey.createSurvey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOptionDto {
    private String name;
    private int order;
    private String imageUrl;
}
