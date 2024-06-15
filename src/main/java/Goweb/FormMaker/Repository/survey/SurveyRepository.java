package Goweb.FormMaker.Repository.survey;

import Goweb.FormMaker.Domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {

}
