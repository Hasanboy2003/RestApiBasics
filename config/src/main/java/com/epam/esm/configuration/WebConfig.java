package com.epam.esm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Web Configuration
 */

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {



}


