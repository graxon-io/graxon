package io.graxon.wait_for_keystore;

import io.graxon.library.node.NodeRegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Optional;

/**
 *
 */
@EnableConfigurationProperties(ConnectionProperties.class)
@SpringBootApplication
public class Application implements CommandLineRunner, ExitCodeGenerator {

	//
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	// initialize with default 0
	private int exitCode;

	//
	@Value("${spring.application.name}")
	private String applicationName;

	//
	private final ConnectionProperties properties;

	/**
	 * Constructor
	 * @param properties connection properties
	 */
    public Application(ConnectionProperties properties) {
        this.properties = properties;
    }

    //
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	/**
	 *
     */
	@Override
	public void run(String... args) throws Exception {
		log.info("executing: command line runner");
		for (int i = 0; i < args.length; ++i) {
			log.trace("args[{}]: {}", i, args[i]);
		}

		//
		if(args.length == 0) {
			log.error("no arguments provided");
			this.exitCode = 1;
		}
		else {

			// create connector
			var connector = new Connector(properties, applicationName);

			// register the node
			Optional<NodeRegistrationResponse> nrr = Optional.empty();
			while (nrr.isEmpty()) {
				nrr = connector.register();
				if(nrr.isEmpty()) {
					log.warn("node registration failed, retrying...");
					Thread.sleep(5000);
				}
			}
			var registration = nrr.get();

			//
			for (int i = 0; i < 10; ++i) {
				log.info("waiting for keystore file...");

				// check if the keystore file exists
				Thread.sleep(1000);
			}
		}
	}

	/**
	 * Override the exit code
	 * @return exit code
	 */
	@Override
	public int getExitCode() {
		return this.exitCode;
	}
}
