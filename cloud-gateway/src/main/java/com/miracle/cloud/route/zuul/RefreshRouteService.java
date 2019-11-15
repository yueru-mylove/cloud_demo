package com.miracle.cloud.route.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月20日 17:08:00
 */
@Service
public class RefreshRouteService {

    private final ApplicationEventPublisher publisher;

    private final RouteLocator routeLocator;

    @Autowired
    public RefreshRouteService(ApplicationEventPublisher publisher, RouteLocator routeLocator) {
        this.publisher = publisher;
        this.routeLocator = routeLocator;
    }

    public void refreshRoute() {
        RoutesRefreshedEvent event = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(event);
    }
}
