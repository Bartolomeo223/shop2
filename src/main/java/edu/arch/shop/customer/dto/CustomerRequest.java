package edu.arch.shop.customer.dto;

import java.math.BigDecimal;

public record CustomerRequest(
        String name,
        String lastName,
        String emailAddress
) {
}
