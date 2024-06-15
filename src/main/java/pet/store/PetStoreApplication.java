package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This class contains the main method to start the SpringBoot Application.
 * 
 * The SpringBootApplication annotation starts the component scan in the pet park package.
 */

@SpringBootApplication
public class PetStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetStoreApplication.class, args);

  }

}
