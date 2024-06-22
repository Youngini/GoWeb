package Goweb.FormMaker.DTO.survey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SurveyListDto {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean activation;
    private String hashtag;
}
