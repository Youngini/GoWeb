package Goweb.FormMaker.DTO.Option;

import Goweb.FormMaker.domain.survey.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOptionDto {
    private String name;
    private int order;
    private String imageUrl;
    private Question question;

}
