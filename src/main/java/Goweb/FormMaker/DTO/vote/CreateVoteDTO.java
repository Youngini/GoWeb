package Goweb.FormMaker.DTO.vote;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class CreateVoteDTO {

    @NotEmpty(message = "투표제목은 필수입니다.")
    private String name;

    private String description;

    @NotEmpty(message = "시작일은 필수입니다.")
    private LocalDate startDate;

    @NotEmpty(message = "한명의 후보자는 필요합니다.")
    private List<String> candidates;

    @NotEmpty(message = "종료일은 필수입니다.")
    private LocalDate endDate;
}
