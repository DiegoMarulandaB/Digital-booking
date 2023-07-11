package com.digital.DigitaBooking.models.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
public class ImageLoaderDTO implements Serializable {
    private List<MultipartFile> images;
}
