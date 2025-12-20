package br.com.fiap.restaurant.application.ports.inbound.auth.input;

public record AuthenticateUserInput(String login, String password) {}