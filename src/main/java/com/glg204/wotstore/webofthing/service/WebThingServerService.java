package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import io.webthings.webthing.Thing;
import io.webthings.webthing.WebThingServer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebThingServerService {

    @Value("${app.webthingserver.port}")
    private int port;

    private List<Thing> things = new ArrayList<>();

    @Autowired
    ThingInStoreDAO thingInStoreDAO;

    @PostConstruct
    public void init() {
        connect();
        startThings(); //ajouter toutes les things déja démarré à la liste pour le démarrage
    }

    private void connect() {
        try {
            WebThingServer server = new WebThingServer(new WebThingServer.MultipleThings(things, "WOTThings"),
                    port);
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            server.start(false);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    private void startThings() {
        List<Optional<ThingInStore>> list = thingInStoreDAO.getThingsInStore();
        for (Optional<ThingInStore> l : list) {
            if (l.isPresent()) {
                ThingInStore thingInStore = l.get();
                if (thingInStore.isStarted()) {
                    startThing(thingInStore.getThing());
                }
            }
        }
    }

    public boolean startThing(Thing thing) {
        if (things.stream().noneMatch(t -> t.getId().equals(thing.getId()))) {
            return things.add(thing);
        }
        return false;
    }

    public boolean stopThing(ThingInStore thingInStore) {
        if (thingInStore.isStarted()) {
            return things.removeIf(thing -> thing.getId().equals(thingInStore.getThing().getId()));
        } else {
            return false;
        }
    }
}

