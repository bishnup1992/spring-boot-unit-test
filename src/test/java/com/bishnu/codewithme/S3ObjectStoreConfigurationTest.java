import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AwsAPISaltConfigTest {

    private AwsCredentialConfig mockAwsCredentialConfig;
    private AwsAPISaltConfig awsAPISaltConfig;

    @Value("${scribe.salt.key}")
    private String scribeSaltKey = "scribeKey";

    @Value("${rest.salt.key}")
    private String restSaltKey = "restKey";

    @BeforeEach
    public void setUp() {
        AWSSecretsManager mockSecretManager = mock(AWSSecretsManager.class);
        mockAwsCredentialConfig = new AwsCredentialConfig(mockSecretManager);

        awsAPISaltConfig = new AwsAPISaltConfig();
        ReflectionTestUtils.setField(awsAPISaltConfig, "scribeSaltKey", scribeSaltKey);
        ReflectionTestUtils.setField(awsAPISaltConfig, "restSaltKey", restSaltKey);
    }

    @Test
    public void testScribeApiSalt() {
        when(mockAwsCredentialConfig.getSecret(anyString())).thenReturn("scribeSecret");

        String scribeSalt = awsAPISaltConfig.scribeApiSalt(mockAwsCredentialConfig);
        assertEquals("scribeSecret", scribeSalt);
    }

    @Test
    public void testRestApiSalt() {
        when(mockAwsCredentialConfig.getSecret(anyString())).thenReturn("restSecret");

        String restSalt = awsAPISaltConfig.restApiSalt(mockAwsCredentialConfig);
        assertEquals("restSecret", restSalt);
    }
}
