package it.uniroma3.siw.festival;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.festival.model.Utente;
import it.uniroma3.siw.festival.repository.UtenteRepository;


// Questa classe viene eseguita automaticamente
// quando avviamo l'applicazione.
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;

    // Serve per codificare le password con BCrypt
    // prima di salvarle nel database.
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {


        // =========================
        // UTENTE USER
        // =========================

        Utente tommy = utenteRepository.findByUsername("tommy");

        if (tommy == null) {

            // Se Tommy non esiste, lo creiamo
            // direttamente con password codificata.
            tommy = new Utente(
                    "tommy",
                    passwordEncoder.encode("password"),
                    "USER"
            );

            utenteRepository.save(tommy);

        } else if (!tommy.getPassword().startsWith("$2")) {

            // Tommy esiste già ma ha ancora
            // la vecchia password salvata in chiaro.
            //
            // La sostituiamo con la versione BCrypt.
            tommy.setPassword(
                    passwordEncoder.encode("password")
            );

            tommy.setRuolo("USER");

            utenteRepository.save(tommy);
        }


        // =========================
        // UTENTE ADMIN
        // =========================

        Utente admin = utenteRepository.findByUsername("admin");

        if (admin == null) {

            // Se Admin non esiste, lo creiamo
            // con password codificata.
            admin = new Utente(
                    "admin",
                    passwordEncoder.encode("admin"),
                    "ADMIN"
            );

            utenteRepository.save(admin);

        } else if (!admin.getPassword().startsWith("$2")) {

            // Admin esiste già ma ha ancora
            // la password in chiaro.
            //
            // La convertiamo in BCrypt.
            admin.setPassword(
                    passwordEncoder.encode("admin")
            );

            admin.setRuolo("ADMIN");

            utenteRepository.save(admin);
        }
    }
}