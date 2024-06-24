package Goweb.FormMaker.dto.survey.surveyResponses;

import Goweb.FormMaker.domain.survey.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestionDto {
    @JsonProperty
    private Long questionId;
    @JsonProperty
    private Integer num;
    @JsonProperty
    private String content;
    @JsonProperty
    private QuestionType questionType;
    @JsonProperty
    private String imageUrl;
    @JsonProperty
    private List<UserResponseDto> selectedOptions;
}
