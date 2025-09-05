package com.sushil.expressway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sushil.expressway.models.ServiceableResponse;
import com.sushil.expressway.models.TatRequest;
import com.sushil.expressway.models.TatResponse;

import reactor.core.publisher.Mono;

@Service

public class UtilService {

    @Value("${server.dtdc.check-serviceable}")
    private String serviableUrl;

    private String src = "400022";

    @Autowired
    private WebClient WebClient;

    public Mono<ServiceableResponse> getService(Integer Dest) {
        String url = "https://ebookingbackend.dtdc.in/serviceableDelivery?src=" + src + "&dst=" + Dest;
        return WebClient.get()
                .uri(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(status -> status.value() == 403,
                    response -> Mono.error(new RuntimeException("DTDC API access forbidden - check API credentials")))
                .bodyToMono(ServiceableResponse.class);
    }

    public Mono<Object> getTat(TatRequest request) {
        String url = "https://ebookingbackend.dtdc.in/getPriceAndTAT";
        return WebClient
                .post()
                .uri(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.value() == 403,
                    response -> Mono.error(new RuntimeException("DTDC API access forbidden - check API credentials")))
                .bodyToMono(TatResponse.class)
                .map(tatResponse -> tatResponse.getExplain().stream()
                        .filter(ex -> !(ex instanceof String))
                        .findFirst());
    }

}
