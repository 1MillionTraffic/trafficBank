package com.trafficbank.trafficbank.aspect;

import com.trafficbank.trafficbank.anootation.ShortLocker;
import com.trafficbank.trafficbank.anootation.ShortLockerContainer;
import com.trafficbank.trafficbank.service.LettuceService;
import com.trafficbank.trafficbank.util.StringBuilderEx;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class ShortLockerAspect {
    private final LettuceService lettuceService;

    @Around("@annotation(com.trafficbank.trafficbank.anootation.ShortLocker)")
    public Object shortLocker(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ShortLocker shortLocker = method.getAnnotation(ShortLocker.class);
        String key = getLockKey(shortLocker, joinPoint);

        if (lettuceService.lock(key, shortLocker.expire())) {
            throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
        }

        Object result = joinPoint.proceed();

        if (shortLocker.unlock()) {
            lettuceService.unlock(key);
        }

        return result;
    }

    @Around("@annotation(com.trafficbank.trafficbank.anootation.ShortLockerContainer)")
    public Object shortLockerContainer(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ShortLockerContainer shortLockerContainer = method.getAnnotation(ShortLockerContainer.class);

        List<Pair<ShortLocker, String>> shortLockerAndKeyList = new ArrayList<>();

        for (ShortLocker shortLocker : shortLockerContainer.value()) {
            String key = getLockKey(shortLocker, joinPoint);

            if (lettuceService.lock(key, shortLocker.expire())) {
                shortLockerAndKeyList.forEach((pair) -> lettuceService.asyncUnlock(pair.getSecond()));

                throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
            }

            shortLockerAndKeyList.add(Pair.of(shortLocker, key));
        }

        Object result = joinPoint.proceed();

        shortLockerAndKeyList.forEach((pair) -> {
            if (pair.getFirst().unlock()) {
                lettuceService.asyncUnlock(pair.getSecond());
            }
        });

        return result;
    }

    private String getLockKey(ShortLocker shortLocker, ProceedingJoinPoint joinPoint) {
        return StringBuilderEx.format(shortLocker.key(), joinPoint.getArgs());
    }

}
