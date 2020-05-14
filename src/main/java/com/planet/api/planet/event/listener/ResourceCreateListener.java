package com.planet.api.planet.event.listener;

import com.planet.api.planet.event.ResourceCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCreateListener implements ApplicationListener<ResourceCreateEvent> {
    @Override
    public void onApplicationEvent(ResourceCreateEvent resourceCreateEvent) {

        HttpServletResponse response = resourceCreateEvent.getResponse();
        String id = resourceCreateEvent.getId();

        addHeaderLocation(response, id);
    }

    private void addHeaderLocation(HttpServletResponse response, String id) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
