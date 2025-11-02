package com.examly.springapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS (Cross-Origin Resource Sharing) configuration for the application.
 * This class implements WebMvcConfigurer to provide a single, centralized
 * location for defining CORS rules, rather than adding @CrossOrigin annotations
 * to every controller.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures the CORS settings for the entire application.
     * @param registry The CorsRegistry to which CORS mappings are added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        /* 
         * Add a CORS mapping for all endpoints starting with "/**"  
         */
        registry.addMapping("/**")
        
        /*
         * Spring will match the pattern and set the `Access-Control-Allow-Origin` header
         * to the exact origin of the request, which is required when `allowCredentials` is true. 
         */
        .allowedOrigins("http://localhost:8081")
        
        /* 
         * Allows the specified HTTP methods from cross-origin requests. 
         */
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        
        /* 
         * Specifies the allowed headers that can be used in cross-origin requests.
         * This is crucial for passing security tokens like Authorization headers. 
         */
        .allowedHeaders("Authorization", "Content-Type")
        
        /*
         * Enables support for credentials (e.g., cookies, HTTP authentication)
         * to be included in cross-origin requests. 
         */
        .allowCredentials(true); 
    }
}
