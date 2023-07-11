package com.digital.DigitaBooking.models.dtos;

public record ValidationError(String field, String error) {
}

// Esta clase representa un objeto que contiene información sobre un error de validación en
// un campo específico. Tiene dos atributos: "field" y "error", ambos de tipo String.
// Proporciona una forma sencilla de almacenar información sobre un error de validación,
// incluyendo el campo afectado y el mensaje de error correspondiente.