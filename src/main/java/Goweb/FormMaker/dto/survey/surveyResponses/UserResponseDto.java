package Goweb.FormMaker.dto.survey.surveyResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    @JsonProperty
    private Long studentId;
    @JsonProperty
    private String studentName;
    @JsonProperty
    private List<String> response; // response Option의 name 또는 answer 저장
}

