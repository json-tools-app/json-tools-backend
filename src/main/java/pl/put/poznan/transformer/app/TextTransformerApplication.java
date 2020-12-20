package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class
 *
 * @author Martyna Mirkiewicz
 * @version 1.0
 */

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer"})
public class TextTransformerApplication {

    /**
     * Main method which starts all processes
     *
     * @param args
     */

    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);
    }
}
