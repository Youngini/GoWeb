package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {

}
