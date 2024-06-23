package Goweb.FormMaker.service.vote;

import Goweb.FormMaker.dto.vote.AllVotesDTO;
import Goweb.FormMaker.dto.vote.CreateVoteDTO;
import Goweb.FormMaker.dto.vote.VoteResultDTO;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.domain.vote.Candidate;
import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.domain.vote.VoteParticipation;
import Goweb.FormMaker.repository.user.UserRepository;
import Goweb.FormMaker.repository.vote.CandidateRepository;
import Goweb.FormMaker.repository.vote.VoteParticipationRepository;
import Goweb.FormMaker.repository.vote.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteParticipationRepository voteParticipationRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    //vote 생성하는 로직
    @Transactional
    public Vote createVote(CreateVoteDTO createVoteDTO){
        Vote vote = new Vote();
        vote.setName(createVoteDTO.getName());
        vote.setDescription(createVoteDTO.getDescription());
        vote.setStartDate(createVoteDTO.getStartDate());
        vote.setEndDate(createVoteDTO.getEndDate());

        for (String candidateName : createVoteDTO.getCandidates()) {
            Candidate candidate = new Candidate();
            candidate.setName(candidateName);
            candidate.setCount(0);
            vote.addCandidate(candidate);
        }

        voteRepository.save(vote);
        return vote;
    }

    //투표삭제
    @Transactional
    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }

    //vote 끝내기 -> vote 종료시간 바꿈
    @Transactional
    public void endVote(Long voteId){
        Vote vote = findVoteById(voteId);
        vote.setEndDate(LocalDate.now());
        voteRepository.flush();
    }

    @Transactional(readOnly = true)
    public List<VoteResultDTO> getVoteResults(Long voteId) {
        List<VoteParticipation> participations = voteParticipationRepository.findAllByVoteId(voteId);

        Map<String, Long> voteCounts = participations.stream()
                .collect(Collectors.groupingBy(participation -> participation.getCandidate().getName(), Collectors.counting()));

        return voteCounts.entrySet().stream()
                .map(entry -> new VoteResultDTO(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AllVotesDTO> getAllVotes() {
        List<Vote> votes = voteRepository.findAll();
        List<AllVotesDTO> allVotesDTOS = new ArrayList<>();
        for (Vote vote : votes) {
            AllVotesDTO allVotesDTO = new AllVotesDTO(vote.getId(),vote.getName());
            allVotesDTOS.add(allVotesDTO);
        }
        return allVotesDTOS;
    }

    public Vote findVoteById(Long voteId){
        return voteRepository.findById(voteId).orElseThrow(()-> new EntityNotFoundException("Vote를 찾을 수 없습니다."));
    }

    @Transactional
    public void participateInVote(Long voteId, Long userId, Long candidateId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new IllegalArgumentException("Invalid vote ID"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new IllegalArgumentException("Invalid candidate ID"));

        // 유저가 이미 해당 투표에 참여했는지 확인
        Optional<VoteParticipation> existingParticipation = voteParticipationRepository.findByVoteIdAndUserId(voteId, userId);
        if (existingParticipation.isPresent()) {
            throw new IllegalStateException("이미 투표에 참여하였습니다.");
        }

        VoteParticipation voteParticipation = new VoteParticipation();
        voteParticipation.setVote(vote);
        voteParticipation.setUser(user);
        voteParticipation.setCandidate(candidate);

        candidate.setCount(candidate.getCount()+1);
        candidateRepository.flush();
        voteParticipationRepository.save(voteParticipation);
    }
}

