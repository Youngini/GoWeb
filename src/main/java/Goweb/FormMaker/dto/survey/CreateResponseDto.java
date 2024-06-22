package Goweb.FormMaker.dto.survey;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateResponseDto {

    private Long userId;
    private Long surveyId;
    private Long questionId;
    private String answer;
    private Set<CreateResponseOptionDto> responseOptions;

}
