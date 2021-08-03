package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ignite.events.CacheEvent;

import java.io.Serializable;
import java.util.Collection;
@Data
@AllArgsConstructor
public class Response implements Serializable {
    private int[] enabledEvents;
    private int cacheSize;
    private Collection<CacheEvent> cacheEvents;
}
