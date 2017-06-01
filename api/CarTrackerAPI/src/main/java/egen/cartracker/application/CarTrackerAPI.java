package egen.cartracker.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication(scanBasePackages={"egen.cartracker.application"})
@EnableEmailTools
public class CarTrackerAPI {

	public static void main(String[] args) {
		SpringApplication.run(CarTrackerAPI.class, args);
	}
}
