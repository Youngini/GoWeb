package Goweb.FormMaker.repository.survey;


import Goweb.FormMaker.domain.survey.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {

}
