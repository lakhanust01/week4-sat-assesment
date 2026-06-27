package com.ust.sdet;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("OMS")
@PactBroker(url = "http://localhost:9292",
        enablePendingPacts = "true",
        providerTags = "main",
        includeWipPactsSince = "2026-06-26"

)
class OmsProviderVerificationTest {

    @BeforeAll
    static void startServer() {
        WireMockOmsServer.start();
    }

    @AfterAll
    static void stopServer() {
        WireMockOmsServer.stop();
    }

    @BeforeEach
    void target(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 4010));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("order 1 exists")
    void orderExists() {
        System.out.println("Preparing Order 1");
    }

    @State("order can be created")
    void orderCanBeCreated() {
        System.out.println("Preparing Create Order");
    }

    @State("inventory exists")
    void inventoryExists() {
        System.out.println("Preparing Inventory");
    }
}