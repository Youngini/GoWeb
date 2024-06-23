package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.domain.survey.Survey;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    public final QuestionRepository questionRepository;
    public final SurveyRepository surveyRepository;

    public QuestionService(QuestionRepository questionRepository, SurveyRepository surveyRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
    }

    public List<Question> getAllQuestions(Long surveyId) {
        // 특정 설문조사에 대한 모든 질문 정보 가져오기
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        List<Question> questions = survey.getQuestions();
        return questions;
    }

}
