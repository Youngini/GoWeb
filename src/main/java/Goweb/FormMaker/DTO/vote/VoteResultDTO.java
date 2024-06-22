package Goweb.FormMaker.DTO.vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class VoteResultDTO {

    private String candidateName;

    private int candidateCount;
}
