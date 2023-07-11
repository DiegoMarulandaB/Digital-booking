package com.digital.DigitaBooking.models.entities.score;

import com.digital.DigitaBooking.models.entities.Tour;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_tour", referencedColumnName = "id")
    private Tour tour;
    private Double average;
    private int oneStar;
    private int twoStars;
    private int treeStars;
    private int fourStars;
    private int fiveStars;

    public Counter(Tour tour) {
        this.tour = tour;
        this.average = 0.0;
    }

    public Counter() {
        this.average = 0.0;
    }

    public void recalculateAndSetAverage() {
        int sum = oneStar + 2 * twoStars + 3 * treeStars + 4 * fourStars + 5 * fiveStars;
        // Representa la contribución total de todas las estrellas en los votos.
        int count = oneStar + twoStars + treeStars + fourStars + fiveStars;
        // Este recuento representa el número total de votos realizados.
        if (count == 0) {
            average = 0.0;
        } else {
            average = (double) sum / (double) count;
        }
    }

    public void addVote(Integer value) {
        if (value == 1) {
            this.oneStar++;
        } else if (value == 2) {
            this.twoStars++;
        } else if (value == 3) {
            this.treeStars++;
        } else if (value == 4) {
            this.fourStars++;
        } else if (value == 5) {
            this.fiveStars++;
        }
        recalculateAndSetAverage();
        // Aquí se incrementan los contadores de estrellas según el valor del voto y luego recalcula el promedio
        // de puntuación.
    }

    public void discountVote(Integer value) {
        if (value == 1) {
            this.oneStar--;
        } else if (value == 2) {
            this.twoStars--;
        } else if (value == 3) {
            this.treeStars--;
        } else if (value == 4) {
            this.fourStars--;
        } else if (value == 5) {
            this.fiveStars--;
        }
        recalculateAndSetAverage();
    }

}
