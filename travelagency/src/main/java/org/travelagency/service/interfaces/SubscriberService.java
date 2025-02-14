package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddSubscriberDTO;

public interface SubscriberService {

    Result addSubscriber(AddSubscriberDTO addSubscriptionDTO);
}
