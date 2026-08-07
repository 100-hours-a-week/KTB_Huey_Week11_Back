package com.community.demo.auth.temp;

import com.community.demo.comments.Comment;
import com.community.demo.exception.UnauthorizedException;
import com.community.demo.posts.entity.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class Authorizer {

    @Around("@annotation(AuthorizedOnly)")
    public Object Authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object property = args[0];
        long authorId = 0;
        if (property instanceof Post post) {
            authorId = post.getAuthor().getId();
        } else if (property instanceof Comment comment) {
            authorId = comment.getAuthor().getId();
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        HttpSession session = request.getSession(false);
        java.lang.Long userId = (java.lang.Long) session.getAttribute("userId");

        if (userId != authorId) {
            throw new UnauthorizedException("unauthorized");
        }

        return joinPoint.proceed();
    }
}
