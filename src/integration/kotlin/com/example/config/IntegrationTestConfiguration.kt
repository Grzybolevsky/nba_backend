package com.example.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@TestConfiguration
class IntegrationTestConfiguration : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.authorizeRequests()
            .antMatchers("/")
            .permitAll()
    }
}