package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.travelagency.service.interfaces.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "travelagency/src/main/resources/static/images/excursions/";

    @Override
    public List<String> saveImages(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);

                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                imageUrls.add("/images/" + fileName);
            }
        }

        return imageUrls;
    }
}
