package org.travelagency.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FlagService {

    String saveDestinationImage(MultipartFile file) throws IOException;
}
