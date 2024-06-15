package Goweb.FormMaker.Repository.survey;


import Goweb.FormMaker.Domain.survey.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {

}
