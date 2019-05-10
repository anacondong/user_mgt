package integration_test;

import com.ecom.user_mgt.UserMgtApplication;
import com.ecom.user_mgt.model.dto.Orders;
import com.ecom.user_mgt.model.dto.UserDetailsRequest;
import com.ecom.user_mgt.model.dto.UserResponse;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static com.ecom.user_mgt.utils.Utility.createURLWithPort;
import static com.ecom.user_mgt.utils.Utility.getHttpEntity;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserMgtApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //private WireMockServer wireMockServer;

    private ObjectMapper objectMapper;

    private Users userOne;

    private Users userTwo;

    private Orders orders;

    @BeforeEach
    public void initSetup() {

        this.userOne = new Users(1L, "Avi", "U", LocalDateTime.now());
        this.userTwo = new Users(2L, "Dad", "G", LocalDateTime.now());
        userRepository.saveAll(Arrays.asList(userOne, userTwo));

        this.orders = new Orders(1L, 1L, 1L, "Ordering Apple", LocalDateTime.now());
        this.objectMapper = new ObjectMapper();
        /*
        this.wireMockServer = new WireMockServer(7000);
        this.wireMockServer.start();*/
    }

    /**
     *
     * Remove Comment for Wire MOCK to Test with Stubs
     *
     */
/*    @AfterEach
    public void tearDown() {
        this.wireMockServer.stop();
    }

    public void setupStub() throws Exception {
        wireMockServer.stubFor(get("/ecom-od/api/orders/user/1")
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(this.objectMapper.writeValueAsString(new UserResponse(userOne, Collections.emptyList())))));

    }*/

    @Test
    public void getUserDetails() throws Exception {
        // setupStub();
        HttpEntity<UserDetailsRequest> requestHttpEntity = getHttpEntity(null);
        ResponseEntity<String> exchange = restTemplate.exchange(createURLWithPort("/api/user/1", port), HttpMethod.GET, requestHttpEntity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertNotNull(exchange.getBody());
        String expected = objectMapper.writeValueAsString(new UserResponse(userOne, Collections.emptyList()));
        assertEquals(expected, exchange.getBody());
    }
}
