package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This class contains the main method to start the SpringBoot Application.
 */

@SpringBootApplication //The SpringBootApplication annotation starts the component scan in the pet park package
public class PetStoreApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(PetStoreApplication.class, args);

  }

}
