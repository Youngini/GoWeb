package Goweb.FormMaker.repository.vote;

import Goweb.FormMaker.domain.vote.Candidate;
import Goweb.FormMaker.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

}
