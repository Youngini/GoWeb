package Goweb.FormMaker.Repository.survey;

import Goweb.FormMaker.Domain.survey.SurveyParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation,Long> {

}
