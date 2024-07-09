package amch.labs.adapter.config;

import amch.labs.application.CartService;
import amch.labs.application.ddd.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackageClasses = {CartService.class},
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ANNOTATION,
          classes = {UseCase.class})
    })
public class DomainConfiguration {}
