package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.SurveyParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation,Long> {

    List<SurveyParticipation> findBySurveyId(Long surveyId);
}
