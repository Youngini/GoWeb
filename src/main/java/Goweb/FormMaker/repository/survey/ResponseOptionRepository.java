package Goweb.FormMaker.repository.survey;

import Goweb.FormMaker.domain.survey.Response;
import Goweb.FormMaker.domain.survey.ResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseOptionRepository extends JpaRepository<ResponseOption, Integer> {
    void deleteAllByResponse(Response existingResponse);
}
