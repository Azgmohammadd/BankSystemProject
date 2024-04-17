package services;

import com.java.banksystemproject.model.authentication.AuthenticationRequest;
import com.java.banksystemproject.model.authentication.AuthenticationResponse;
import com.java.banksystemproject.model.authentication.RegisterRequest;
import com.java.banksystemproject.model.constant.Role;
import com.java.banksystemproject.service.IAuthenticationService;
import com.java.banksystemproject.service.factory.AuthenticationServiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationServiceTest {
    private static IAuthenticationService authenticationService;
    private static RegisterRequest registerRequest;
    private static AuthenticationRequest authenticationRequest;


    @BeforeAll
    public static void setUp() {
        registerRequest = RegisterRequest.builder()
                .firstName("firstNameRegReq")
                .lastName("lastNameRegReq")
                .nationalCode("12345678")
                .username("usernameRegReq")
                .password("passwordRegReq")
                .role(Role.USER)
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .username("usernameRegReq")
                .password("passwordRegReq")
                .build();

        authenticationService = new AuthenticationServiceFactory().getJDBC();
    }

    @Test
    @Order(1)
    public void registerTest() {
        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals(response.getResponseMessage(), "registered");
    }

    @Test
    @Order(2)
    public void authenticateTest() {
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals(response.getResponseMessage(), "authenticated");
    }
}
