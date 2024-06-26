package Goweb.FormMaker.dto.survey.updateSurvey;

import Goweb.FormMaker.domain.survey.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionDto {
    private int order;
    private String content;
    private QuestionType questionType;
    private String imageUrl;
    private List<UpdateOptionDto> options;

}
