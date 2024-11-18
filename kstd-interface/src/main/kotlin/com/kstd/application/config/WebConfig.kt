package com.kstd.application.config

import com.kstd.domain.config.KstdDomainConfig
import com.kstd.h2.config.H2Config
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableAutoConfiguration
//@AutoConfigureAfter(value = [H2Config::class])
@Import(value = [H2Config::class, KstdDomainConfig::class])
@EnableWebMvc
class WebConfig {
}