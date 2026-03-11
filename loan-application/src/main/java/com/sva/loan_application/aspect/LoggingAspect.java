package com.sva.loan_application.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Around("execution(* com.sva.loan_application.controller.*.*(..))")
    public Object logAroundControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        // Mask sensitive data in logs
        Object[] maskedArgs = maskSensitiveData(args);
        
        logger.info("Method: {} - Arguments: {}", methodName, maskedArgs);
        
        Object result = joinPoint.proceed();
        
        // Mask sensitive data in response
        Object maskedResult = maskSensitiveData(result);
        
        logger.info("Method: {} - Response: {}", methodName, maskedResult);
        
        return result;
    }
    
    private Object[] maskSensitiveData(Object[] args) {
        if (args == null) return null;
        
        Object[] maskedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            maskedArgs[i] = maskObject(args[i]);
        }
        
        return maskedArgs;
    }
    
    private Object maskSensitiveData(Object obj) {
        return maskObject(obj);
    }
    
    private Object maskObject(Object obj) {
        if (obj == null) return null;
        
        try {
            // Convert object to JSON
            String json = objectMapper.writeValueAsString(obj);
            ObjectNode node = (ObjectNode) objectMapper.readTree(json);
            
            // Mask sensitive fields
            maskField(node, "fullName");
            maskField(node, "phone");
            maskField(node, "email");
            maskField(node, "address");
            maskField(node, "dob");
            
            // Business loan specific fields
            maskField(node, "businessName");
            maskField(node, "ownerName");
            maskField(node, "ownerEmail");
            maskField(node, "ownerPhone");
            
            return node;
        } catch (Exception e) {
            // If can't convert to JSON, return toString with masking
            String str = obj.toString();
            // Apply regex masking for common patterns
            str = str.replaceAll("(?<=email=)[^,}]+", "****@****.com");
            str = str.replaceAll("(?<=phone=)[^,}]+", "**********");
            str = str.replaceAll("(?<=businessName=)[^,}]+", "********");
            str = str.replaceAll("(?<=ownerName=)[^,}]+", "********");
            return str;
        }
    }
    
    private void maskField(ObjectNode node, String fieldName) {
        if (node.has(fieldName)) {
            String value = node.get(fieldName).asText();
            String masked = maskValue(fieldName, value);
            node.put(fieldName, masked);
        }
    }
    
    private String maskValue(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        switch (fieldName) {
            case "email":
            case "ownerEmail":
                return value.replaceAll("(?<=.{2}).*(?=@)", "****");
            case "phone":
            case "ownerPhone":
                return value.replaceAll("(?<=^.{2}).*(?=.{2}$)", "******");
            case "fullName":
            case "ownerName":
                String[] parts = value.split(" ");
                StringBuilder maskedName = new StringBuilder();
                for (String part : parts) {
                    if (part.length() > 1) {
                        maskedName.append(part.charAt(0)).append("*".repeat(part.length() - 1)).append(" ");
                    } else {
                        maskedName.append(part).append(" ");
                    }
                }
                return maskedName.toString().trim();
            case "businessName":
                if (value.length() > 2) {
                    return value.substring(0, 2) + "*".repeat(value.length() - 2);
                }
                return value;
            default:
                return "********";
        }
    }
}