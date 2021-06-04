package com.cs492e.vocali

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "colab.model")
class DefaultProperties {

    var endpoint: String = ""
}