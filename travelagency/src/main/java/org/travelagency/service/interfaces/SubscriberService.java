package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.entity.Subscriber;
import org.travelagency.model.importDTO.AddSubscriberDTO;

import java.util.List;

public interface SubscriberService {

    Result addSubscriber(AddSubscriberDTO addSubscriptionDTO);

    List<Subscriber> getAllSubscribers();
}
