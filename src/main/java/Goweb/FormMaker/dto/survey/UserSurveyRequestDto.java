package Goweb.FormMaker.dto.survey;

import Goweb.FormMaker.domain.survey.Option;
import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.domain.survey.ResponseOption;
import Goweb.FormMaker.domain.survey.Survey;
import Goweb.FormMaker.domain.user.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Set;

// 사용자의 설문 조사 응답을 가져오기 위한 DTO
@Setter
public class UserSurveyRequestDto {

    private Long responseId;

    private Question question;

    private String answer;

    private Set<Option> Options;
}
