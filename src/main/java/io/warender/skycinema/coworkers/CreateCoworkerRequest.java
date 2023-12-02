package io.warender.skycinema.coworkers;

import io.warender.skycinema.auth.UserRole;

public record CreateCoworkerRequest(String email, String password, UserRole role) {}
