package com.eazybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    private static final Logger log = LoggerFactory.getLogger(ResponseTraceFilter.class);

    final
    FilterUtility filterUtility;

    public ResponseTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain
                .filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = filterUtility.getCorrelationId(requestHeaders);

                    if (!(exchange.getResponse().getHeaders().containsKey(filterUtility.CORRELATION_ID))) {
                        log.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                        exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
                    }
                }));
    }
}
