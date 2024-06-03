import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AwsAPISaltConfigTest {

    @Test
    public void testScribeApiSalt_Success() {
        // Mock AwsCredentialConfig
        AwsCredentialConfig mockAwsCredentialConfig = Mockito.mock(AwsCredentialConfig.class);
        when(mockAwsCredentialConfig.getSecret("scribe.salt.key")).thenReturn("scribeSecret");

        // Create AwsAPISaltConfig instance
        AwsAPISaltConfig awsAPISaltConfig = new AwsAPISaltConfig();

        // Call the method under test
        String scribeApiSalt = awsAPISaltConfig.scribeApiSalt(mockAwsCredentialConfig);

        // Assert that the returned scribeApiSalt matches the expected value
        assertEquals("scribeSecret", scribeApiSalt);
    }

    @Test
    public void testRestApiSalt_Success() {
        // Mock AwsCredentialConfig
        AwsCredentialConfig mockAwsCredentialConfig = Mockito.mock(AwsCredentialConfig.class);
        when(mockAwsCredentialConfig.getSecret("rest.salt.key")).thenReturn("restSecret");

        // Create AwsAPISaltConfig instance
        AwsAPISaltConfig awsAPISaltConfig = new AwsAPISaltConfig();

        // Call the method under test
        String restApiSalt = awsAPISaltConfig.restApiSalt(mockAwsCredentialConfig);

        // Assert that the returned restApiSalt matches the expected value
        assertEquals("restSecret", restApiSalt);
    }
}
