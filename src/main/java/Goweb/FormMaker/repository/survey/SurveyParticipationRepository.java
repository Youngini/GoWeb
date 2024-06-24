package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.SurveyParticipation;
import Goweb.FormMaker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation,Long> {

    List<SurveyParticipation> findBySurveyId(Long surveyId);

    List<SurveyParticipation> findUserBySurveyId(Long surveyId);
}
