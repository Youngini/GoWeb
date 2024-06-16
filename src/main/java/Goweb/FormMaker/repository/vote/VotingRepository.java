package Goweb.FormMaker.repository.vote;

import Goweb.FormMaker.domain.vote.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Voting,Long> {

}
