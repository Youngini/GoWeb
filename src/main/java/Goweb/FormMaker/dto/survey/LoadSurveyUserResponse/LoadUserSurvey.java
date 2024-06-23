package Goweb.FormMaker.dto.survey.LoadSurveyUserResponse;

import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
public class LoadUserSurvey {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String hashtag;
    private List<LoadQuestionDto> questions;
}
