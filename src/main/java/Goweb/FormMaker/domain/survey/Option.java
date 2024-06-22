package Goweb.FormMaker.domain.survey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
