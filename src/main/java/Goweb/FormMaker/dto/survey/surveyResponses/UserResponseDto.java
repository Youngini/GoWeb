package Goweb.FormMaker.dto.survey.surveyResponses;

import lombok.Setter;

import java.util.List;

@Setter
public class UserResponseDto {
    private Long studentId;
    private String studentName;
    private List<String> response; // response Option의 name 또는 answer 저장
}
