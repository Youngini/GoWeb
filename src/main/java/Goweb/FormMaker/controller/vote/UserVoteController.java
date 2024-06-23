package Goweb.FormMaker.controller.vote;

import Goweb.FormMaker.dto.vote.AllVotesDTO;
import Goweb.FormMaker.dto.vote.VoteResultDTO;
import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.service.vote.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class UserVoteController {

    private final VoteService voteService;

    @Autowired
    public UserVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "투표 목록보기")
    @GetMapping
    public ResponseEntity<List<AllVotesDTO>> getAllVotes() {
        List<AllVotesDTO> allVotes = voteService.getAllVotes();
        return ResponseEntity.ok(allVotes);
    }

    @Operation(summary = "투표 결과보기")
    @GetMapping("/{voteId}/results")
    public ResponseEntity<List<VoteResultDTO>> getVoteResults(@PathVariable Long voteId) {
        List<VoteResultDTO> voteResults = voteService.getVoteResults(voteId);
        return ResponseEntity.ok(voteResults);
    }

    @Operation(summary = "투표 세부내용 보기")
    @GetMapping("/{voteId}")
    public ResponseEntity<Vote> getVoteDetail(@PathVariable Long voteId) {
        Vote vote = voteService.findVoteById(voteId);
        return ResponseEntity.ok(vote);
    }

    @Operation(summary = "투표 하기")
    @PostMapping("/{voteId}/participate")
    public ResponseEntity<Void> participateInVote(@PathVariable Long voteId, @RequestParam Long userId, @RequestParam Long candidateId) {
        voteService.participateInVote(voteId, userId, candidateId);
        return ResponseEntity.ok().build();
    }
}
