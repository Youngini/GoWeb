package Goweb.FormMaker.repository.survey;


import Goweb.FormMaker.domain.survey.Response;
import Goweb.FormMaker.domain.survey.Survey;
import Goweb.FormMaker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findBySurveyId(Long surveyId);

    List<Response> findBySurveyAndUser(Survey survey, User user);

    List<Response> findByQuestionId(Long id);

    Optional<Response> findBySurveyIdAndUserId(Long surveyId, Long userId);

    List<Response> findBySurvey(Survey survey);
}
