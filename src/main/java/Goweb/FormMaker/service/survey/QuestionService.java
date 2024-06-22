package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Getter
    public QuestionRepository questionRepository;

    public List<Question> getAllQuestions(Long surveyId) {
        // 특정 설문조사에 대한 모든 질문 정보 가져오기
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        return questions;
    }

}
