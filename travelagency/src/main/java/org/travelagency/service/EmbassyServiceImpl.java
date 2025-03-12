package org.travelagency.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Embassy;
import org.travelagency.repository.EmbassyRepository;
import org.travelagency.service.interfaces.EmbassyService;

import java.util.Optional;

@Service
public class EmbassyServiceImpl implements EmbassyService {

    private final EmbassyRepository embassyRepository;

    public EmbassyServiceImpl(EmbassyRepository embassyRepository) {
        this.embassyRepository = embassyRepository;
    }

    @Override
    public void saveAndFlushEmbassy(Embassy embassy) {
        this.embassyRepository.saveAndFlush(embassy);
    }

    @Override
    public Optional<Embassy> findEmbassyByName(String name) {
        return this.embassyRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteEmbassyByCountryName(String destinationName) {
        this.embassyRepository.deleteByName(destinationName);
    }
}
