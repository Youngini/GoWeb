package Goweb.FormMaker.dto.survey.loadSurvey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class LoadSurveyDto {

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean activation;
    private String hashtag;
    private List<LoadQuestionDto> questions;

}
