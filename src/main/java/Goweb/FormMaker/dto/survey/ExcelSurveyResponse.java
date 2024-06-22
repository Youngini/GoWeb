package Goweb.FormMaker.dto.survey;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExcelSurveyResponse {

    private String name;
    private Long studentNumber;
    private List<UserResponseDto> selectedAnswers;

}
