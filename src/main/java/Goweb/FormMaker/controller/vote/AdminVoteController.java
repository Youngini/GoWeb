package Goweb.FormMaker.controller.vote;

import Goweb.FormMaker.DTO.vote.CreateVoteDTO;
import Goweb.FormMaker.DTO.vote.VoteResultDTO;
import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.service.vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/votes")
public class AdminVoteController {

    private final VoteService voteService;

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        return ResponseEntity.ok(votes);
    }

    @PostMapping("/create")
    public ResponseEntity<Vote> createVote(@RequestBody @Valid CreateVoteDTO createVoteDTO) {
        Vote vote = voteService.createVote(createVoteDTO);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long voteId) {
        voteService.deleteVote(voteId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{voteId}/stop")
    public ResponseEntity<Void> stopVote(@PathVariable Long voteId) {
        voteService.endVote(voteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{voteId}/results")
    public ResponseEntity<List<VoteResultDTO>> getVoteResults(@PathVariable Long voteId) {
        List<VoteResultDTO> voteResults = voteService.getVoteResults(voteId);
        return ResponseEntity.ok(voteResults);
    }



}
