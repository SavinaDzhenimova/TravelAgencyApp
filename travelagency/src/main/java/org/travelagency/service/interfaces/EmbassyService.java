package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Embassy;

import java.util.Optional;

public interface EmbassyService {
    void saveAndFlushEmbassy(Embassy embassy);

    Optional<Embassy> findEmbassyByName(String name);
}
