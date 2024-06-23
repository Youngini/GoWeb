package Goweb.FormMaker.dto.survey.surveyResponses;

import Goweb.FormMaker.domain.survey.QuestionType;
import lombok.Setter;

import java.util.List;

@Setter
public class SurveyQuestionDto {
    private Long questionId;
    private Integer num;
    private String content;
    private QuestionType questionType;
    private String imageUrl;
    private List<UserResponseDto> selectedOptions;
}
