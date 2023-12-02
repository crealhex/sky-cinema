package io.warender.skycinema.customers;

public record CreateCustomerRequest(
    String email, String password
) {}
