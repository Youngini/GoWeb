package Goweb.FormMaker.dto.survey.userResponse;

import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
public class UserSurveyDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String hashtag;
    private List<UserQuestionDto> questions;
}
