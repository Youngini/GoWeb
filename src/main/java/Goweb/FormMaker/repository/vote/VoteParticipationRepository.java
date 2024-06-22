package Goweb.FormMaker.repository.vote;

import Goweb.FormMaker.domain.vote.VoteParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteParticipationRepository extends JpaRepository<VoteParticipation,Long> {
    List<VoteParticipation> findAllByVoteId(Long voteId);
    Optional<VoteParticipation> findByVoteIdAndUserId(Long voteId, Long userId);
}
