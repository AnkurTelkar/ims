package com.ims.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;*/
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

/**
 * Created by ekansh on 12/7/15.
 */
@Configuration
@EnableCaching
public class AppConfiguration {

	@PersistenceContext
	private EntityManager entityManager;
    public TilesConfigurer tilesConfigurer() {
    	
    	TilesConfigurer tilesConfigurer = new TilesConfigurer();
        String[] defs = { "WEB-INF/tiles.xml" };
        tilesConfigurer.setDefinitions(defs);
        return tilesConfigurer;
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
    	 UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
         tilesViewResolver.setViewClass(TilesView.class);
         return tilesViewResolver;
    }
    
}
