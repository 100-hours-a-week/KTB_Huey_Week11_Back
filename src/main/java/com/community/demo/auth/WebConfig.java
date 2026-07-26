package com.community.demo.auth;

import com.community.demo.auth.temp.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath()
                .toString();

        String uploadPath = "file:///" + rootPath + "/uploads/";

        registry.addResourceHandler("/public/**")
                .addResourceLocations(uploadPath);
    }

    /**
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users",
                        "/users/login",
                        "/users/me/profile-image",
                        "/public/**",
                        "/users/dup/**"
                )
                .excludeHttpMethods(HttpMethod.OPTIONS);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserHandlerResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "OPTIONS", "PATCH", "PUT", "DELETE", "HEAD")
                .allowedHeaders("Content-Type")
                .allowCredentials(true);
    }
    **/
}
