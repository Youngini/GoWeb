package Goweb.FormMaker.Repository.vote;

import Goweb.FormMaker.Domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

}
