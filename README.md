# Festival SIW

Progetto assegnato per il corso di **Sistemi Informativi su Web (SIW)** – Università Roma Tre.

**Studente:** Tommaso Felizioli  
**Matricola:** 634508  
**Anno accademico:** 2025/2026

## Descrizione

L'applicazione permette la gestione di festival cinematografici, film, registi, sale, proiezioni e recensioni.

Il progetto è sviluppato con un'architettura a livelli basata su **Spring Boot**, **JPA/Hibernate** e **PostgreSQL**.  
L'interfaccia server-side utilizza **Thymeleaf**, mentre una parte dell'applicazione è realizzata con **React** e comunica con il backend attraverso API REST.

## Tecnologie utilizzate

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- PostgreSQL
- Thymeleaf
- Maven

### Frontend
- React
- JavaScript
- CSS
- Vite
- REST API

## Architettura

Il backend segue una struttura a livelli:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Le principali entità del dominio sono:

- Festival
- Film
- Regista
- Sala
- Proiezione
- Recensione
- Utente

## Funzionalità principali

### Funzionalità pubbliche

Gli utenti non autenticati possono:

- visualizzare i festival;
- visualizzare i dettagli di un festival;
- consultare i film associati ai festival;
- visualizzare le proiezioni;
- consultare i dettagli dei film e dei registi;
- leggere le recensioni.

### Utente registrato

Un utente autenticato con ruolo `USER` può:

- inserire una recensione;
- modificare le proprie recensioni;
- eliminare le proprie recensioni.

Ogni utente può inserire al massimo una recensione per ciascun film.

### Amministratore

Un utente con ruolo `ADMIN` può gestire:

- festival;
- film;
- registi;
- sale;
- associazioni tra film e festival;
- proiezioni.

Sono inoltre presenti controlli sulle principali regole di business, tra cui:

- impossibilità di programmare proiezioni sovrapposte nella stessa sala;
- possibilità di programmare un film solamente all'interno di un festival a cui è associato;
- data della proiezione compresa tra la data di inizio e la data di fine del festival.

## Sicurezza

L'applicazione utilizza **Spring Security** per autenticazione e autorizzazione.

Sono previsti i ruoli:

- `USER`
- `ADMIN`

Le operazioni amministrative sono protette e un utente può modificare o eliminare solamente le proprie recensioni.

## Transazioni

La logica applicativa è gestita nel Service Layer tramite transazioni.

Le operazioni di sola lettura utilizzano transazioni `readOnly`, mentre le operazioni di modifica vengono eseguite all'interno di transazioni di aggiornamento.

La creazione e modifica delle proiezioni costituisce inoltre un caso d'uso che coinvolge più entità e repository e viene gestito atomicamente.

## Test prestazionale

È presente il test:

```text
src/test/java/it/uniroma3/siw/festival/PerformanceTest.java
```

Il test confronta due strategie di caricamento dei dati:

- caricamento `LAZY`;
- caricamento tramite `JOIN FETCH`.

Nel test effettuato sullo stesso insieme di dati sono stati ottenuti:

```text
LAZY
Query eseguite: 3
Tempo medio: circa 26.35 ms

JOIN FETCH
Query eseguite: 1
Tempo medio: circa 11.33 ms
```

Il confronto evidenzia la riduzione del numero di query verso il database ottenuta tramite `JOIN FETCH`.

## Frontend React

Il frontend React è contenuto nella directory:

```text
festival-frontend/
```

Il frontend utilizza le API REST esposte dal backend per visualizzare:

- festival;
- film;
- dettagli dei festival;
- programmazione delle proiezioni;
- informazioni sui registi;
- recensioni.

È inoltre disponibile una funzione di ricerca nel catalogo dei film.

## Configurazione del database

Per motivi di sicurezza il file:

```text
src/main/resources/application.properties
```

non è incluso nel repository.

Per eseguire il progetto è necessario configurare localmente la connessione al proprio database PostgreSQL.

## Avvio del frontend

Dalla directory:

```text
festival-frontend
```

installare le dipendenze:

```bash
npm install
```

e avviare l'applicazione:

```bash
npm run dev
```

Il frontend comunica con il backend Spring Boot in esecuzione sulla porta `8080`.

## Autore

**Tommaso Felizioli**  
Matricola **634508**