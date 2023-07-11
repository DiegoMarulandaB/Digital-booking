package com.digital.DigitaBooking.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
// Esta línea importa la clase PageImpl de Spring Data, que proporciona una implementación
// de la interfaz Page para representar una página de resultados paginados.
import org.springframework.data.domain.Pageable;

import java.util.List;

// Esta anotación se aplica a la clase y especifica las propiedades que deben ser
// ignoradas al serializar o deserializar el objeto JSON. En este caso, las propiedades
// mencionadas se ignorarán.
@JsonIgnoreProperties({"pageable", "last", "size", "sort", "first", "numberOfElements", "empty"})
public class PageResponseDTO<T> extends PageImpl {
// Se declara una clase llamada "PageResponseDTO" que extiende la clase PageImpl<T>, donde
// T es el tipo de elementos que contiene la página.

    public PageResponseDTO(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageResponseDTO(List<T> content) {
        super(content);
    }
}
