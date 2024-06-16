package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.SurveyParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation,Long> {

}
