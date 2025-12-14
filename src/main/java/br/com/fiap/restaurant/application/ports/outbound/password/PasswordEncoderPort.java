package br.com.fiap.restaurant.application.ports.outbound.password;

public interface PasswordEncoderPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
