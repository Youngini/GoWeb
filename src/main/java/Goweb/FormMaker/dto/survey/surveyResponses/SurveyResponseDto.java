package Goweb.FormMaker.dto.survey.surveyResponses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SurveyResponseDto {

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean activation;
    private String hashtag;
    private List<SurveyQuestionDto> questions;

}
