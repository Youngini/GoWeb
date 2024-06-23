package Goweb.FormMaker.dto.survey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserSurveyList {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String hashtag;
}
