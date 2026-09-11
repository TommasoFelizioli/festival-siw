package it.uniroma3.siw.festival;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import jakarta.persistence.EntityManagerFactory;
import it.uniroma3.siw.festival.service.FestivalService;

@SpringBootTest
public class PerformanceTest {

    @Autowired
    private FestivalService festivalService;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    /*
     * CONFRONTO DELLE STRATEGIE DI CARICAMENTO: LAZY vs JOIN FETCH
     *
     * Con LAZY, Hibernate carica le associazioni solo quando vengono
     * effettivamente utilizzate. Nel nostro test sono state eseguite
     * 3 query SQL: una per il Festival, una per i Film associati e
     * una per il Regista richiesto.
     *
     * Con JOIN FETCH, invece, Festival, Film e Registi vengono caricati
     * insieme mediante una singola query SQL.
     *
     * RISULTATI DEL TEST (Festival con ID 1, 2 Film):
     * LAZY       -> 3 query SQL, tempo medio 21,81 ms
     * JOIN FETCH -> 1 query SQL, tempo medio 16,66 ms
     *
     * JOIN FETCH riduce quindi il numero di accessi al database.
     * Questo vantaggio può diventare più importante quando aumentano
     * i dati, perché il caricamento LAZY può causare il problema N+1:
     * una query iniziale seguita da ulteriori query per caricare
     * le associazioni degli oggetti.
     *
     * I tempi misurati non rappresentano un valore assoluto: possono
     * variare a causa del warm-up della JVM, della cache e dello stato
     * del database. Nel nostro test, infatti, la prima esecuzione è
     * risultata più lenta delle successive.
     *
     * Il dato più significativo del confronto è quindi la riduzione
     * delle query SQL da 3 a 1, a parità di Film caricati.
     */
    
    @Test
    public void testLazy() {

        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        Statistics statistics = sessionFactory.getStatistics();

        double tempoTotale = 0;

        for (int i = 0; i < 5; i++) {

            statistics.clear();

            long inizio = System.nanoTime();

            var festival = festivalService.findByIdLazy(1L);

            long fine = System.nanoTime();

            int numeroFilm = festival.getFilms().size();

            double tempo = (fine - inizio) / 1_000_000.0;
            tempoTotale += tempo;

            System.out.println(
                    "Esecuzione " + (i + 1) +
                    " - Film: " + numeroFilm +
                    " - Query: " + statistics.getPrepareStatementCount() +
                    " - Tempo: " + tempo + " ms"
            );
        }

        System.out.println(
                "TEMPO MEDIO LAZY: " + tempoTotale / 5 + " ms"
        );
    }
    
    @Test
    public void testJoinFetch() {

        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        Statistics statistics = sessionFactory.getStatistics();

        double tempoTotale = 0;

        for (int i = 0; i < 5; i++) {

            statistics.clear();

            long inizio = System.nanoTime();

            var festival =
                    festivalService.findByIdWithFilmsAndRegisti(1L);

            long fine = System.nanoTime();

            int numeroFilm = festival.getFilms().size();

            double tempo = (fine - inizio) / 1_000_000.0;
            tempoTotale += tempo;

            System.out.println(
                    "Esecuzione " + (i + 1) +
                    " - Film: " + numeroFilm +
                    " - Query: " + statistics.getPrepareStatementCount() +
                    " - Tempo: " + tempo + " ms"
            );
        }

        System.out.println(
                "TEMPO MEDIO JOIN FETCH: " + tempoTotale / 5 + " ms"
        );
    }
    
    
    
  

}