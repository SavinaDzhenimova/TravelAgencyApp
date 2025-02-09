package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Continent;

import java.util.Optional;

public interface ContinentService {
    Optional<Continent> findContinentByName(String name);
}
