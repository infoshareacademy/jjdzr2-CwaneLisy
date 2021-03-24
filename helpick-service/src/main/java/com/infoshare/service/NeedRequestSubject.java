package com.infoshare.service;

import com.infoshare.domain.NeedRequest;

import java.util.ArrayList;
import java.util.List;

public abstract class NeedRequestSubject {

    protected List<NeedRequestObserver> observers = new ArrayList();

    public void attach(NeedRequestObserver needRequestObserver){
        if(!observers.contains(needRequestObserver)){
            observers.add(needRequestObserver);
        }
    }

    public void detach(NeedRequestObserver needRequestObserver){
        if(observers.contains(needRequestObserver)){
            observers.remove(needRequestObserver);
        }
    }

    protected void notify(EventType eventType, NeedRequest needRequest){
        for (NeedRequestObserver observer: observers) {
            observer.update(eventType, needRequest);
        }
    }
}
