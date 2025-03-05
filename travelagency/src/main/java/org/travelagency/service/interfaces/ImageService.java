package org.travelagency.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import org.travelagency.model.entity.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<String> saveImages(List<MultipartFile> files) throws IOException;

    void deleteImagesFromDirectory(List<Image> images) throws IOException;

    void deleteAllImagesByExcursionId(Long excursionId);

    List<Image> findAllImagesByExcursionId(Long excursionId);
}
