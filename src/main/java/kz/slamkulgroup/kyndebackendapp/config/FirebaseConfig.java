package kz.slamkulgroup.kyndebackendapp.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);
    
    @Value("${app.firebase.service-account-key-path:firebase-service-account.json}")
    private String serviceAccountKeyPath;
    
    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(serviceAccountKeyPath).getInputStream());
            
            FirebaseOptions firebaseOptions = FirebaseOptions
                    .builder()
                    .setCredentials(googleCredentials)
                    .build();
            
            FirebaseApp app;
            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(firebaseOptions, "KyndeBackendApp");
            } else {
                app = FirebaseApp.getInstance("KyndeBackendApp");
            }
            
            logger.info("Firebase initialized successfully");
            return FirebaseMessaging.getInstance(app);
            
        } catch (Exception e) {
            logger.warn("Firebase initialization failed: {}. Running without FCM support.", e.getMessage());
            return null;
        }
    }
}