
package edu.arch.shop.customer.dto;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String emailAddress
) {
}