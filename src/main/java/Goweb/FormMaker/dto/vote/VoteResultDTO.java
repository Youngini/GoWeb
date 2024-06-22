package Goweb.FormMaker.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class VoteResultDTO {

    private String candidateName;

    private int candidateCount;
}
