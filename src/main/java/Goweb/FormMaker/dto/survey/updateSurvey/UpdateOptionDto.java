package Goweb.FormMaker.dto.survey.updateSurvey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOptionDto {
    private Long id;
    private int order;
    private String name;
    private String imageUrl;
}
