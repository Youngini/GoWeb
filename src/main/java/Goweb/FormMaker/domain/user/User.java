package Goweb.FormMaker.domain.user;

import Goweb.FormMaker.domain.survey.SurveyParticipation;
import Goweb.FormMaker.dto.auth.JoinAdminRequestDTO;
import Goweb.FormMaker.dto.auth.JoinRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private Long studentNumber;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public static User createFromJoin(JoinRequestDTO userJoinRequestDTO, BCryptPasswordEncoder encoder) {
        return User.builder()
                .name(userJoinRequestDTO.getName())
                .studentNumber(userJoinRequestDTO.getStudentNumber())
                .password(encoder.encode(userJoinRequestDTO.getPassword()))
                .userType(UserType.STUDENT)
                .createdAt(LocalDate.now())
                .build();
    }

    public static User createAdminFromJoin(JoinAdminRequestDTO userJoinRequestDTO, BCryptPasswordEncoder encoder) {
        return User.builder()
                .name(userJoinRequestDTO.getName())
                .studentNumber(userJoinRequestDTO.getStudentNumber())
                .password(encoder.encode(userJoinRequestDTO.getPassword()))
                .userType(UserType.ADMIN)
                .createdAt(LocalDate.now())
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyParticipation> surveyParticipations;
}
