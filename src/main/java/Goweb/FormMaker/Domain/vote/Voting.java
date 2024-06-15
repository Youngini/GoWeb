package Goweb.FormMaker.Domain.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voting_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String imageUrl;

    /*@Column(nullable = false)
    private List<VotingOption> VotingOptions;*/

}
