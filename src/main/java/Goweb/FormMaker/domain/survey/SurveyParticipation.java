package Goweb.FormMaker.domain.survey;

import Goweb.FormMaker.domain.user.User;
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

    @PrePersist
    @PreUpdate
    public void updateSubDate() {
        this.subDate = LocalDate.now();
    }

}
