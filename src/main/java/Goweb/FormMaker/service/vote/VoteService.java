package Goweb.FormMaker.service.vote;

import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.repository.vote.VoteRepository;
import Goweb.FormMaker.repository.vote.VotingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;

    //vote 생성 -> 로직에 Voting 더 넣어야함
    @Transactional
    public Vote createVote(){
        Vote vote = new Vote();
        voteRepository.save(vote);
        return vote;
    }

    //vote 끝내기 -> vote 종료시간 바꿈
    @Transactional
    public Vote endVote(Long voteId){
        Vote vote = findVoteById(voteId);
        vote.setEndDate(LocalDate.now());
        voteRepository.flush();
        return vote;
    }

    public Vote findVoteById(Long voteId){
        return voteRepository.findById(voteId).orElseThrow(()-> new EntityNotFoundException("Vote를 찾을 수 없습니다."));
    }
}
