package Goweb.FormMaker.dto.survey.loadSurvey;

import Goweb.FormMaker.domain.survey.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadQuestionDto {

    private Long questionId;
    private Integer num;
    private String content;
    private QuestionType questionType;
    private String imageUrl;
    private List<LoadOptionDto> options;
}
