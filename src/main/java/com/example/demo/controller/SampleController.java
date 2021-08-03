package com.example.demo.controller;

import com.example.demo.cachestore.CacheStoreCustom;
import com.example.demo.dto.OrderDetails;
import com.example.demo.dto.OrderKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.events.CacheEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.apache.ignite.events.EventType.EVT_CACHE_OBJECT_EXPIRED;

@RestController
public class SampleController {
    @Autowired
    Ignite ignite;
    @Autowired
    CacheStoreCustom cacheStoreCustom;

    @GetMapping("/entry1")
    public ResponseEntity<Response> entryget(){
        IgniteCache<Integer, Integer> my_cache = ignite.cache("MY_CACHE");
        IgniteEvents events = ignite.events();
        Collection<CacheEvent> cacheEvents = events.localQuery(e -> {
            // process the event
            return true;
        }, EVT_CACHE_OBJECT_EXPIRED);

        return new ResponseEntity<>(new Response(events.enabledEvents(),my_cache.size(),cacheEvents), HttpStatus.OK);

    }
    @GetMapping("/entry/{expiry}")
    public void entry(@PathVariable int expiry){


        CacheConfiguration<OrderKey, OrderDetails> cachecfg = new CacheConfiguration<>();

       /* QueryEntity e = new QueryEntity();
        e.setKeyType("java.lang.Integer");
        e.setValueType("java.lang.Integer");

        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("name", "java.lang.String");
        e.setFields(map);

        ArrayList<QueryEntity> list = new ArrayList<QueryEntity>();
        list.add(e);
        cachecfg.setQueryEntities(list);*/
        cachecfg.setName("MY_CACHE");
        /*cachecfg.setExpiryPolicyFactory(new AbstractEvictionPolicyFactory<ExpiryPolicy>() {
            @Override
            public ExpiryPolicy create() {
                return new TouchedExpiryPolicy(Duration.ONE_MINUTE);
            }
        });*/
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(cacheStoreCustom.getClass()));
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);
        IgniteCache<OrderKey, OrderDetails> ignitecache = ignite.getOrCreateCache(cachecfg).withExpiryPolicy(new TouchedExpiryPolicy(new Duration(TimeUnit.MINUTES,expiry))).withKeepBinary();
        ignitecache.put(new OrderKey("123456"+expiry), OrderDetails.builder().orderKey(new OrderKey("123456"+expiry))
                .orderType("abc").orderDate(ZonedDateTime.now()).build());
    }
}
