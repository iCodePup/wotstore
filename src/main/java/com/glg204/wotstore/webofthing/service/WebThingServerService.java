package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import io.webthings.webthing.Thing;
import io.webthings.webthing.WebThingServer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebThingServerService {

    @Value("${app.webthingserver.port}")
    private int port;

    private final List<Thing> things = new ArrayList<>();

    ConcurrentHashMap<Long, WebThingServer> servers = new ConcurrentHashMap<>();

    @Autowired
    ThingInStoreDAO thingInStoreDAO;

    @PostConstruct
    public void init() {
        System.setProperty("java.net.preferIPv4Stack", "true");
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
                    server.start(false);
                    Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

                    return true;
                }
            } else {
                int maxPort = servers.values().stream()
                        .mapToInt(WebThingServer::getListeningPort)
                        .max()
                        .orElse(port);
                maxPort = maxPort + 1;

                WebThingServer server = new WebThingServer(new WebThingServer.SingleThing(thing),
                        maxPort);

                server.start(false);
                servers.putIfAbsent(thingInStoreId, server);
                Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
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

    public boolean deleteThing(long thingInStoreId) {
        boolean result = this.stopThing(thingInStoreId);
        if (servers.containsKey(thingInStoreId)) {
            servers.remove(thingInStoreId);
        }
        return result;
    }
}

