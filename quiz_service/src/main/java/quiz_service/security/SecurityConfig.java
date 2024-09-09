package quiz_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${user.admin.key}")
    private String adminKey;

    @Value("${user.teacher.key}")
    private String teacherKey;

    @Value("${user.student.key}")
    private String studentKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/quiz/**")).authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/registration/**")).authenticated()
                )
                .addFilterBefore(new TokenFilter(adminKey, teacherKey,studentKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // add cors mapping later

//    @Bean
//    public WebMvcConfigurer corsConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://127.0.0.1:5500","http://127.0.0.1:8081");
//            }
//        };
//    }
}

