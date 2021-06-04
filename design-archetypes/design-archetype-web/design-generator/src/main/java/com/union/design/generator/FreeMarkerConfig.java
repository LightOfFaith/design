package com.union.design.generator;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FreeMarkerConfig {

    public static Configuration getConfig() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(
                FreeMarkerConfig.class.getClassLoader().getResource("templates")
        ).getFile()));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return cfg;
    }
}