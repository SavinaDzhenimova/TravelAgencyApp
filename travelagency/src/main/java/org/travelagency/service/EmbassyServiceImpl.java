package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.EmbassyRepository;
import org.travelagency.service.interfaces.EmbassyService;

@Service
public class EmbassyServiceImpl implements EmbassyService {

    private final EmbassyRepository embassyRepository;

    public EmbassyServiceImpl(EmbassyRepository embassyRepository) {
        this.embassyRepository = embassyRepository;
    }
}
