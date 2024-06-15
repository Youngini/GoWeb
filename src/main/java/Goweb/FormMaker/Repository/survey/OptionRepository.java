package Goweb.FormMaker.Repository.survey;

import Goweb.FormMaker.Domain.vote.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {

}
