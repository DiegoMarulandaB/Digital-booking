package com.digital.DigitaBooking.models.entities.score;

import com.digital.DigitaBooking.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_counter", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Counter counter;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    private Integer value;

}
