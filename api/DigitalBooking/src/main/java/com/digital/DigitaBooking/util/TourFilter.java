package com.digital.DigitaBooking.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TourFilter {

    private LocalDate InitialDate;
    private LocalDate FinalDate;
    private Integer countryId;
    private Integer categoryId;
    private Integer offset;
    private Integer limit;
}
