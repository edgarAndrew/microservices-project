package quiz_service.configurations;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Value("${user.student.key}")
    private String studentKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Set the Authorization token
            String token = studentKey; // Replace with your logic to fetch token
            template.header("Authorization", token);
        };
    }
}

