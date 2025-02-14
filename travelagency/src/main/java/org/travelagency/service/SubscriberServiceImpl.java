package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Result;
import org.travelagency.model.entity.Subscriber;
import org.travelagency.model.importDTO.AddSubscriberDTO;
import org.travelagency.repository.SubscriberRepository;
import org.travelagency.service.interfaces.SubscriberService;

import java.util.Optional;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Result addSubscriber(AddSubscriberDTO addSubscriberDTO) {
        if (addSubscriberDTO == null) {
            return new Result(false, "Абонатът не съществува!");
        }

        Optional<Subscriber> optionalSubscriber = this.subscriberRepository.findByEmail(addSubscriberDTO.getEmail());

        if (optionalSubscriber.isPresent()) {
            return new Result(false, "Вече сте абониран за нашия бюлетин!");
        }

        Subscriber subscriber = new Subscriber();

        subscriber.setEmail(addSubscriberDTO.getEmail());

        this.subscriberRepository.saveAndFlush(subscriber);

        return new Result(true, "Вие успешно се абонирахте за нашия бюлетин и " +
                "ще получавате имейл при добавяне на нова екскурзия!");
    }
}
