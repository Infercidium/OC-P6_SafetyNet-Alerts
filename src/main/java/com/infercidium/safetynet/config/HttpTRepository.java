package com.infercidium.safetynet.config;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Operating HttpTrace.
 */
@Component
class HttpTRepository implements HttpTraceRepository {

    /**
     * List containing the requests.
     */
    private final List<HttpTrace> traceList = new ArrayList<>();

    /**
     * Allows the operation of Actuator httpTrace.
     * @return all requests made.
     */
    @Override
    public List<HttpTrace> findAll() {
        return traceList;
    }

    /**
     * Adds the request that is made to the API.
     * @param trace : request.
     */
    @Override
    public void add(final HttpTrace trace) {
        traceList.add(trace);
    }
}
