package com.infercidium.safetynet.config;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class HttpTRepository implements org.springframework.boot.actuate.trace.http.HttpTraceRepository {

    List<HttpTrace> traceList = new ArrayList<>();

    @Override
    public List<HttpTrace> findAll() {
        return traceList;
    }

    @Override
    public void add(HttpTrace trace) {
        traceList.add(trace);
    }
}
