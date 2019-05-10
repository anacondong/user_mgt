package component_test;

import com.ecom.user_mgt.UserMgtApplication;
import com.ecom.user_mgt.model.dto.UserDetailsRequest;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static com.ecom.user_mgt.utils.Utility.createURLWithPort;
import static com.ecom.user_mgt.utils.Utility.getHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserMgtApplication.class)
public class UserApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Users userOne;

    private Users userTwo;


    @BeforeEach
    public void init() {
        this.userOne = new Users(1L, "Avi", "U", LocalDateTime.now());
        this.userTwo = new Users(2L, "Dad", "G", LocalDateTime.now());
        userRepository.saveAll(Arrays.asList(userOne, userTwo));
    }

    @Test
    public void testRecievesAllUsers() throws Exception {

        HttpEntity<UserDetailsRequest> requestHttpEntity =getHttpEntity(new UserDetailsRequest(true, Collections.EMPTY_LIST));
        ResponseEntity<String> exchange = restTemplate.exchange(createURLWithPort("/api/userDetails", port), HttpMethod.POST, requestHttpEntity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertNotNull(exchange.getBody());
        String expected = objectMapper.writeValueAsString(Arrays.asList(userOne, userTwo));
        assertEquals(expected, exchange.getBody());
    }

    @Test
    public void testReceiveOnlySelectedUsers() throws Exception {
        HttpEntity<UserDetailsRequest> requestHttpEntity = getHttpEntity(new UserDetailsRequest(false, Collections.singletonList(1L)));
        ResponseEntity<String> exchange = restTemplate.exchange(createURLWithPort("/api/userDetails", port), HttpMethod.POST, requestHttpEntity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertNotNull(exchange.getBody());
        String expected = objectMapper.writeValueAsString(Collections.singleton(userOne));
        assertEquals(expected, exchange.getBody());

    }

}
