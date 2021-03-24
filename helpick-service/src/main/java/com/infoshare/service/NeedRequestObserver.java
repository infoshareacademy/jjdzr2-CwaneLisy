package com.infoshare.service;

import com.infoshare.domain.NeedRequest;


public interface NeedRequestObserver {

    void update(EventType eventType, NeedRequest needRequest);

}
