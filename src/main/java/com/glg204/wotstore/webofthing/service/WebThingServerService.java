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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

@Service
public class WebThingServerService {

    @Value("${app.webthingserver.port}")
    private int port;

    private List<Thing> things = new ArrayList<>();

    ConcurrentHashMap<Long, WebThingServer> servers = new ConcurrentHashMap<>();

    @Autowired
    ThingInStoreDAO thingInStoreDAO;

    @PostConstruct
    public void init() {
        startThings(); //ajouter toutes les things déja démarré à la liste pour le démarrage
    }

    private void startThings() {

        List<Optional<ThingInStore>> list = thingInStoreDAO.getThingsInStore();
        for (Optional<ThingInStore> l : list) {
            if (l.isPresent()) {
                ThingInStore thingInStore = l.get();
                if (thingInStore.isStarted()) {
                    startThing(thingInStore.getId(), thingInStore.getThing());
                }
            }
        }
    }

    public boolean startThing(long thingInStoreId, Thing thing) {
        try {
            if (servers.containsKey(thingInStoreId)) {
                WebThingServer server = servers.get(thingInStoreId);
                if (!server.isAlive()) {
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
                    server.start(false);
                    return true;
                }
            } else {
                WebThingServer server = new WebThingServer(new WebThingServer.SingleThing(thing),
                        port++);
                server.start(false);
                servers.put(thingInStoreId, server);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean stopThing(long thingInStoreId) {
        if (servers.containsKey(thingInStoreId)) {
            WebThingServer server = servers.get(thingInStoreId);
            if (server.isAlive()) {
                server.stop();
                return true;
            }
        }
        return false;
    }
}

