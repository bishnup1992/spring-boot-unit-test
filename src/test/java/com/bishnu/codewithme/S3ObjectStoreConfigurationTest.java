package com.example.demo;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.s3.model.KMSEncryptionMaterialsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class S3ObjectStoreConfigurationTest {

    @Mock
    private AwsS3Properties awsS3Properties;
    @Mock
    private AwsS3Properties.S3Properties s3Properties;
    @Mock
    private AwsS3Properties.ClientProperties clientProperties;
    @Mock
    private AmazonS3Client amazonS3Client;

    private S3ObjectStoreConfiguration config;
    private String region = "us-west-2";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(awsS3Properties.getS3()).thenReturn(s3Properties);
        when(awsS3Properties.getClient()).thenReturn(clientProperties);
        when(s3Properties.getKeyARN()).thenReturn("arn:aws:kms:us-west-2:123456789012:key/abcd-efgh-ijkl-mnop");
        when(clientProperties.getProxyHost()).thenReturn("proxyHost");
        when(clientProperties.getProxyPort()).thenReturn(8080);
        when(clientProperties.getNonProxyHosts()).thenReturn("nonProxyHosts");

        config = new S3ObjectStoreConfiguration(region);
    }

    @Test
    public void testSecretManagerBean() {
        AWSSecretsManagerClientBuilder mockBuilder = mock(AWSSecretsManagerClientBuilder.class);
        AWSSecretsManager mockSecretsManager = mock(AWSSecretsManager.class);

        when(mockBuilder.withRegion(region)).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockSecretsManager);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AWSSecretsManagerClientBuilder.class, () -> mockBuilder);
            context.register(S3ObjectStoreConfiguration.class);
            context.refresh();

            AWSSecretsManager secretsManager = context.getBean(AWSSecretsManager.class);
            assertNotNull(secretsManager, "AWSSecretsManager bean should not be null");
        }
    }

    @Test
    public void testAmazonS3ClientBean() {
        AmazonS3ClientBuilder mockBuilder = mock(AmazonS3ClientBuilder.class);
        AmazonS3Client mockS3Client = mock(AmazonS3Client.class);

        when(mockBuilder.build()).thenReturn(mockS3Client);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AmazonS3ClientBuilder.class, () -> mockBuilder);
            context.register(S3ObjectStoreConfiguration.class);
            context.refresh();

            AmazonS3Client s3Client = context.getBean(AmazonS3Client.class);
            assertNotNull(s3Client, "AmazonS3Client bean should not be null");
        }
    }

    @Test
    public void testObjectStoreServiceBean() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AwsS3Properties.class, () -> awsS3Properties);
            context.registerBean(AmazonS3Client.class, () -> amazonS3Client);
            context.register(S3ObjectStoreConfiguration.class);
            context.refresh();

            ObjectStoreService objectStoreService = context.getBean(ObjectStoreService.class);
            assertNotNull(objectStoreService, "ObjectStoreService bean should not be null");
        }
    }

    @Test
    public void testS3ClientBean() {
        KMSEncryptionMaterialsProvider materialProvider = new KMSEncryptionMaterialsProvider("arn:aws:kms:us-west-2:123456789012:key/abcd-efgh-ijkl-mnop");
        AmazonS3EncryptionClientV2Builder mockBuilder = mock(AmazonS3EncryptionClientV2Builder.class);
        AmazonS3 mockS3Client = mock(AmazonS3.class);
        ClientConfiguration clientConfiguration = new ClientConfiguration();

        when(mockBuilder.withEncryptionMaterialsProvider(materialProvider)).thenReturn(mockBuilder);
        when(mockBuilder.withClientConfiguration(clientConfiguration)).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockS3Client);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AmazonS3EncryptionClientV2Builder.class, () -> mockBuilder);
            context.registerBean(ClientConfiguration.class, () -> clientConfiguration);
            context.register(S3ObjectStoreConfiguration.class);
            context.refresh();

            AmazonS3 s3Client = context.getBean(AmazonS3.class);
            assertNotNull(s3Client, "AmazonS3 bean should not be null");
        }
    }
}
