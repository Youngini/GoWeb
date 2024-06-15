package Goweb.FormMaker.Repository.vote;

import Goweb.FormMaker.Domain.vote.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Voting,Long> {

}
