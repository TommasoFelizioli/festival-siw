// Questo è il package principale del progetto.
// Da qui parte tutta la nostra applicazione.

package it.uniroma3.siw.festival;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Questa annotazione dice a Spring Boot che questa è
//la classe principale da cui configurare e avviare l'applicazione.
@SpringBootApplication
public class FestivalApplication {

	// Il main è il punto da cui parte il programma Java.
    // Quando facciamo "Run As → Spring Boot App",
	// Java comincia da questo metodo.
	public static void main(String[] args) {
		
		// Questa istruzione avvia Spring Boot.
        //
        // Da questo momento Spring prepara tutta l'applicazione:
        // - trova e crea Controller, Service e altri componenti;
        // - configura JPA/Hibernate;
        // - si collega al database;
        // - avvia il server web Tomcat.
        //
        // In pratica questa riga "accende" la nostra applicazione.
		SpringApplication.run(FestivalApplication.class, args);
	}

}
