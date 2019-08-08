package com.github.rsrini7.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Component
public class AppProperties {
    private String version = readVersionFromMaven();

    private String readVersionFromMaven() {
        String implementationVersion = getClass().getPackage().getImplementationVersion();
        return implementationVersion==null?"snapshot":implementationVersion;
    }
}
