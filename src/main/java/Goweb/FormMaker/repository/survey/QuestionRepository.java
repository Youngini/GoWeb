package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findBySurveyId(Long surveyId);
}
