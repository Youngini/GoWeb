package Goweb.FormMaker.controller.vote;

import Goweb.FormMaker.DTO.vote.VoteResultDTO;
import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.service.vote.VoteService;
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

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{voteId}/results")
    public ResponseEntity<List<VoteResultDTO>> getVoteResults(@PathVariable Long voteId) {
        List<VoteResultDTO> voteResults = voteService.getVoteResults(voteId);
        return ResponseEntity.ok(voteResults);
    }


    @PostMapping("/{voteId}/participate")
    public ResponseEntity<Void> participateInVote(@PathVariable Long voteId, @RequestParam Long userId, @RequestParam Long candidateId) {
        voteService.participateInVote(voteId, userId, candidateId);
        return ResponseEntity.ok().build();
    }
}
