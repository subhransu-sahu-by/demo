package com.example.demo.cachestore;

import com.example.demo.dto.OrderDetails;
import com.example.demo.dto.OrderKey;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Component
public class CacheStoreCustom implements CacheStore<OrderKey, OrderDetails>, Serializable {
    @Autowired
    MongoRepositoryCostom repositoryCostom;

    @Override
    public void loadCache(IgniteBiInClosure<OrderKey, OrderDetails> clo, @Nullable Object... args) throws CacheLoaderException {
        System.out.println("loadcache");
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {
        System.out.println("commit");
    }

    @Override
    public OrderDetails load(OrderKey key) throws CacheLoaderException {
        System.out.println("key");
        return null;
    }

    @Override
    public Map<OrderKey, OrderDetails> loadAll(Iterable<? extends OrderKey> keys) throws CacheLoaderException {
        System.out.println("loadall");
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends OrderKey, ? extends OrderDetails> entry) throws CacheWriterException {
        System.out.println("write");
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends OrderKey, ? extends OrderDetails>> entries) throws CacheWriterException {
        System.out.println("writeall");
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        System.out.println("delete");
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {

    }
}
