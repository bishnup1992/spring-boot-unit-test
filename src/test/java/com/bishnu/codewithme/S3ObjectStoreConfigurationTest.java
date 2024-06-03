import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AwsAPISaltConfig.class)
@TestPropertySource(properties = {
        "scribe.salt.key=scribeSaltKey",
        "rest.salt.key=restSaltKey"
})
public class AwsAPISaltConfigTest {

    @Autowired
    private AwsAPISaltConfig awsAPISaltConfig;

    @MockBean
    private AwsCredentialConfig awsCredentialConfig;

    @BeforeEach
    public void setUp() {
        when(awsCredentialConfig.getSecret("scribeSaltKey")).thenReturn("scribeSaltSecret");
        when(awsCredentialConfig.getSecret("restSaltKey")).thenReturn("restSaltSecret");
    }

    @Test
    public void testScribeApiSalt() {
        String scribeSalt = awsAPISaltConfig.scribeApiSalt(awsCredentialConfig);
        assertEquals("scribeSaltSecret", scribeSalt);
    }

    @Test
    public void testRestApiSalt() {
        String restSalt = awsAPISaltConfig.restApiSalt(awsCredentialConfig);
        assertEquals("restSaltSecret", restSalt);
    }
}
