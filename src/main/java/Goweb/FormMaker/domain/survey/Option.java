package Goweb.FormMaker.domain.survey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer order;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResponseOption> responseOptions;

}
