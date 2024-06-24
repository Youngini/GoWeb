package Goweb.FormMaker.controller.vote;

import Goweb.FormMaker.dto.vote.AllVotesDTO;
import Goweb.FormMaker.dto.vote.CreateVoteDTO;
import Goweb.FormMaker.dto.vote.VoteResultDTO;
import Goweb.FormMaker.domain.vote.Vote;
import Goweb.FormMaker.service.vote.VoteService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "모든 투표 목록 보기")
    @GetMapping
    public ResponseEntity<List<AllVotesDTO>> getAllVotes() {
        List<AllVotesDTO> allVotes = voteService.getAllVotes();
        return ResponseEntity.ok(allVotes);
    }

    @Operation(summary = "투표 생성하기")
    @PostMapping("/create")
    public ResponseEntity<Vote> createVote(@RequestBody @Valid CreateVoteDTO createVoteDTO) {
        Vote vote = voteService.createVote(createVoteDTO);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @Operation(summary = "투표 삭제하기")
    @DeleteMapping("/{voteId}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long voteId) {
        voteService.deleteVote(voteId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "투표 종료하기(종료시간 이전에 끝내고 싶을 때)")
    @PutMapping("/{voteId}/stop")
    public ResponseEntity<Void> stopVote(@PathVariable Long voteId) {
        voteService.endVote(voteId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "투표 결과보기")
    @GetMapping("/{voteId}/results")
    public ResponseEntity<List<VoteResultDTO>> getVoteResults(@PathVariable Long voteId) {
        List<VoteResultDTO> voteResults = voteService.getVoteResults(voteId);
        return ResponseEntity.ok(voteResults);
    }



}
