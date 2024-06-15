package Goweb.FormMaker.Domain.survey;

import Goweb.FormMaker.Domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter @Setter
public class SurveyParticipation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survay_participation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private LocalDate subDate;

}
