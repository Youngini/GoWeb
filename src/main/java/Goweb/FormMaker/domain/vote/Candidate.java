package Goweb.FormMaker.domain.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private int count;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
}

