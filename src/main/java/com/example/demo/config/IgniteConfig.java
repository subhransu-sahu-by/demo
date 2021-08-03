package com.example.demo.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.spi.eventstorage.memory.MemoryEventStorageSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.ignite.events.EventType.EVT_CACHE_OBJECT_EXPIRED;

@Configuration
public class IgniteConfig {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Bean
    @Lazy
    public Ignite igniteInstance(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setIncludeEventTypes(EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_READ,
                EventType.EVT_CACHE_OBJECT_REMOVED, EventType.EVT_NODE_JOINED, EventType.EVT_NODE_LEFT, EVT_CACHE_OBJECT_EXPIRED);
        MemoryEventStorageSpi eventStorageSpi = new MemoryEventStorageSpi();
        eventStorageSpi.setExpireAgeMs(600000);
        cfg.setEventStorageSpi(eventStorageSpi);
        Ignite ignite = Ignition.start(cfg);
        localListen(ignite);
        remoteListen(ignite);
        return ignite;

    }
    private void localListen(Ignite ignite) throws IgniteException {
        IgniteEvents events = ignite.events();
        IgnitePredicate<CacheEvent> localListener = evt -> {
            System.out.println("Received event [evt=" + evt.name() + ", key=" + evt.key() + ", oldVal=" + evt.oldValue()
                    + ", newVal=" + evt.newValue());

            return true; // Continue listening.
        };
        events.localListen(localListener, EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_READ,
                EventType.EVT_CACHE_OBJECT_REMOVED,EVT_CACHE_OBJECT_EXPIRED);
    }
    private void remoteListen(Ignite ignite){
        IgniteEvents events = ignite.events();

        IgnitePredicate<CacheEvent> filter = evt -> {
            System.out.println("remote event: " + evt.name());
            return true;
        };
        UUID uuid = events.remoteListen(new IgniteBiPredicate<UUID, CacheEvent>() {

            @Override
            public boolean apply(UUID uuid, CacheEvent e) {

                return true;
            }
        }, filter, EventType.EVT_CACHE_OBJECT_PUT,EVT_CACHE_OBJECT_EXPIRED);
    }


}
