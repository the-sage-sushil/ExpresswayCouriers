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
                        response -> Mono
                                .error(new RuntimeException("DTDC API access forbidden - check API credentials")))
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
                        response -> Mono
                                .error(new RuntimeException("DTDC API access forbidden - check API credentials")))
                .bodyToMono(TatResponse.class)
                .map(tatResponse -> tatResponse.getExplain().stream()
                        .filter(ex -> !(ex instanceof String))
                        .findFirst());
    }

    private static final String[] units = {
            "", "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] tens = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty",
            "Sixty", "Seventy", "Eighty", "Ninety"
    };

    private static String convertBelowThousand(int number) {
        StringBuilder result = new StringBuilder();

        if (number >= 100) {
            result.append(units[number / 100]).append(" Hundred ");
            number %= 100;
        }

        if (number >= 20) {
            result.append(tens[number / 10]).append(" ");
            number %= 10;
        }

        if (number > 0) {
            result.append(units[number]).append(" ");
        }

        return result.toString().trim();
    }

    public static String convert(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();

        if (number >= 10000000) {
            result.append(convertBelowThousand(number / 10000000)).append(" Crore ");
            number %= 10000000;
        }

        if (number >= 100000) {
            result.append(convertBelowThousand(number / 100000)).append(" Lakh ");
            number %= 100000;
        }

        if (number >= 1000) {
            result.append(convertBelowThousand(number / 1000)).append(" Thousand ");
            number %= 1000;
        }

        if (number > 0) {
            result.append(convertBelowThousand(number));
        }

        return result.toString().trim();
    }

}
