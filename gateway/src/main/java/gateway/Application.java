package gateway;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// tag::route-locator[]
	@Bean  //체계적인 class 정의
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		return builder.routes()
			.route(p -> p
				.path("/get")
				.filters(f -> f.addRequestHeader("Hello", "World")) //get경로로 올 경우 헤더에 "Hello":"world"라는 값 추가
				.uri(httpUri))  //해당 링크로 보낸다 http 수정 필요
			.route(p -> p
				.host("*.circuitbreaker.com")//문제가 있는 마이크로서비스로서의 트래픽을 차단, 전체서비스가 느려짐을 방지
				.filters(f -> f
					.circuitBreaker(config -> config
						.setName("mycmd")
						.setFallbackUri("forward:/fallback")))
				.uri(httpUri)) //http수정필요
			.build();
	}
	// end::route-locator[]

	// tag::fallback[]
	@RequestMapping("/fallback") //
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}  //문제가 있는 마이크로서비스에게 보여지는 값?화면?
	// end::fallback[]
}

// tag::uri-configuration[]
@ConfigurationProperties
class UriConfiguration {
	
	private String httpbin = "http://httpbin.org:80";

	public String getHttpbin() {
		return httpbin;
	}

	public void setHttpbin(String httpbin) {
		this.httpbin = httpbin;
	}
}
// end::uri-configuration[]
// end::code[]
