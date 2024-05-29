package com.ssafit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafit.model.dao")
public class MybatisConfig {
}
