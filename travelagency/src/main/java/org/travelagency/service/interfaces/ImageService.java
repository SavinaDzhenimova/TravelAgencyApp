package org.travelagency.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<String> saveImages(List<MultipartFile> files) throws IOException;
}
