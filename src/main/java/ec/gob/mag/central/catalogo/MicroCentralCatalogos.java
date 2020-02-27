package ec.gob.mag.central.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableResourceServer
@SpringBootApplication
public class MicroCentralCatalogos extends SpringBootServletInitializer {
	private static Class<MicroCentralCatalogos> applicationClass = MicroCentralCatalogos.class;

	public static void main(String[] args) {
		SpringApplication.run(MicroCentralCatalogos.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
}
