package com.unnamed.b.atv.sample;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class LogoutEventBus {

    public static Integer logOutEvent = 1;

    private PublishSubject<Object> bus = PublishSubject.create();
    private static LogoutEventBus instance;

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public static LogoutEventBus getInstance() {

        //synchronized block to remove overhead
        synchronized (LogoutEventBus.class) {
            if (instance == null) {
                // if instance is null, initialize
                instance = new LogoutEventBus();
            }

        }

        return instance;
    }

}

