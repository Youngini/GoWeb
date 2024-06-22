package Goweb.FormMaker.repository.survey;


import Goweb.FormMaker.domain.survey.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findBySurveyId(Long surveyId);
}
