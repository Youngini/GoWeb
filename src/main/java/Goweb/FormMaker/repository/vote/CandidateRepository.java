package Goweb.FormMaker.repository.vote;

import Goweb.FormMaker.domain.vote.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

}
