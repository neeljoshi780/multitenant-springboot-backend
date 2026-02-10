package com.multitenant.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapperConfig
 *
 * Configures ModelMapper for DTO and entity mapping.
 *
 * Provides a centralized mapping configuration
 * used across application services.
 */
@Configuration
public class ModelMapperConfig {

	/**
	 * Creates and configures ModelMapper bean.
	 */
	@Bean
	ModelMapper modelMapper() {
		// Create ModelMapper instance
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setSkipNullEnabled(true) // Enable skipping null values during mapping
			.setMatchingStrategy(MatchingStrategies.STRICT); // Enforce strict property matching strategy
		// Return configured ModelMapper bean
		return modelMapper;
	}

}