package com.digital.DigitaBooking.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class ImageCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String imageTitle;

    @Column(columnDefinition = "VARCHAR(1000)")
    @NotNull
    private String imageUrl;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="id_category", referencedColumnName = "id")
    private Category category;

}
