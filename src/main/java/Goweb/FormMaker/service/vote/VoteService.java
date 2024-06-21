package Goweb.FormMaker.service.vote;

import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.repository.vote.VoteRepository;
import Goweb.FormMaker.repository.vote.VotingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;

    @Transactional
    public Vote createVote(){
        Vote vote = new Vote();
        voteRepository.save(vote);
        return vote;
    }
}
