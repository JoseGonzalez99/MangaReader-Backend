package com.hotbox.jaitymangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.audit")
public class AuditProperties {
    private int ttlDays = 0; // por defecto, sin expiración
}
