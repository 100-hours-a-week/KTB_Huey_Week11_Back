package com.community.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath()
                .toString();

        String uploadPath = "file:///" + rootPath + "/uploads/";

        registry.addResourceHandler("/public/**")
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/users/me/profile-image",
                        "/public/**"
                );

        registry.addInterceptor(authorizationInterceptor)
                .includeHttpMethods(HttpMethod.PATCH, HttpMethod.DELETE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserHandlerResolver());
    }
}
