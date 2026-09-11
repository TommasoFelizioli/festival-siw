import { useEffect, useState } from 'react'
import './App.css'

const API = 'http://localhost:8080/api'

// Converte la data da yyyy-MM-dd a dd/MM/yyyy.
function formatDate(date) {
  if (!date) return ''

  const [year, month, day] = date.split('-')
  return `${day}/${month}/${year}`
}

function App() {

  const [festivals, setFestivals] = useState([])
  const [films, setFilms] = useState([])

  const [selectedFestival, setSelectedFestival] = useState(null)
  const [selectedFilm, setSelectedFilm] = useState(null)

  const [festivalMovies, setFestivalMovies] = useState([])
  const [screenings, setScreenings] = useState([])
  const [reviews, setReviews] = useState([])

  const [loading, setLoading] = useState(true)
  const [detailsLoading, setDetailsLoading] = useState(false)

  const [error, setError] = useState('')
  const [search, setSearch] = useState('')


  // Esegue una richiesta REST e restituisce il JSON.
  async function getJson(url) {

    const response = await fetch(url)

    if (!response.ok) {
      throw new Error('Errore nel caricamento dei dati')
    }

    return response.json()
  }


  // Carica Festival e Film all'apertura dell'applicazione.
  useEffect(() => {

    Promise.all([
      getJson(`${API}/festivals`),
      getJson(`${API}/movies`)
    ])
      .then(([festivalsData, filmsData]) => {

        setFestivals(festivalsData)
        setFilms(filmsData)

      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))

  }, [])


  // Apre il dettaglio di un Festival.
  async function showFestival(festival) {

    setError('')
    setDetailsLoading(true)

    setSelectedFilm(null)
    setSelectedFestival(festival)

    try {

      const [moviesData, screeningsData] = await Promise.all([

        getJson(
          `${API}/festivals/${festival.id}/movies`
        ),

        getJson(
          `${API}/festivals/${festival.id}/screenings`
        )

      ])

      setFestivalMovies(moviesData)
      setScreenings(screeningsData)

    } catch (err) {

      setError(err.message)

    } finally {

      setDetailsLoading(false)

    }
  }


  // Apre il dettaglio di un Film
  // e carica le relative Recensioni.
  async function showFilm(film) {

    setError('')
    setDetailsLoading(true)

    setSelectedFilm(film)

    try {

      const reviewsData = await getJson(
        `${API}/movies/${film.id}/reviews`
      )

      setReviews(reviewsData)

    } catch (err) {

      setError(err.message)

    } finally {

      setDetailsLoading(false)

    }
  }


  // Torna alla pagina iniziale.
  function goHome() {

    setSelectedFestival(null)
    setSelectedFilm(null)

    setError('')
  }


  // Filtra i Film in base alla ricerca.
  const filteredFilms = films.filter(film => {

    const text = search.trim().toLowerCase()

    if (!text) {
      return true
    }

    return (
      film.title?.toLowerCase().includes(text) ||
      film.genre?.toLowerCase().includes(text) ||
      film.countryProduction?.toLowerCase().includes(text) ||
      film.registaNome?.toLowerCase().includes(text) ||
      film.registaCognome?.toLowerCase().includes(text)
    )
  })


  return (

    <main className="app-shell">


      {/* HEADER */}

      <section className="hero">

        <div className="hero-content">

          <p className="eyebrow">
            FESTIVAL CINEMATOGRAFICI
          </p>

          <h1>
            Festival del Cinema
          </h1>

          <p className="hero-text">
            Esplora festival, film, registi,
            proiezioni e recensioni.
          </p>

        </div>


        <div className="hero-stats">

          <div className="hero-badge">

            <span>
              {festivals.length}
            </span>

            <small>
              Festival
            </small>

          </div>


          <div className="hero-badge">

            <span>
              {films.length}
            </span>

            <small>
              Film
            </small>

          </div>

        </div>

      </section>



      {/* PULSANTE HOME */}

      {(selectedFestival || selectedFilm) && (

        <div className="top-actions">

          <button
            className="back-button"
            onClick={goHome}
          >
            ← Home
          </button>

        </div>

      )}



      {/* MESSAGGI */}

      {loading && (

        <p className="message">
          Caricamento...
        </p>

      )}


      {error && (

        <p className="error">
          {error}
        </p>

      )}


      {detailsLoading && (

        <p className="message">
          Caricamento dettagli...
        </p>

      )}



      {/* HOME */}

      {!loading &&
        !selectedFestival &&
        !selectedFilm && (

          <>


            {/* FESTIVAL */}

            <section className="section-block">

              <div className="section-header">

                <div>

                  <p className="section-label">
                    EVENTI
                  </p>

                  <h2>
                    Festival
                  </h2>

                </div>

              </div>


              <div className="festival-row">

                {festivals.map(festival => (

                  <button
                    className="festival-card"
                    key={festival.id}
                    onClick={() =>
                      showFestival(festival)
                    }
                  >

                    <div className="festival-card-top">

                      <span className="festival-year">
                        {festival.anno}
                      </span>

                    </div>


                    <h3>
                      {festival.nome}
                    </h3>


                    <p>
                      {festival.citta}
                    </p>


                    <div className="date-range">

                      {formatDate(
                        festival.dataInizio
                      )}

                      <span>
                        →
                      </span>

                      {formatDate(
                        festival.dataFine
                      )}

                    </div>

                  </button>

                ))}

              </div>

            </section>



            {/* CATALOGO FILM */}

            <section className="section-block">

              <div className="catalog-header">

                <div>

                  <p className="section-label">
                    CATALOGO
                  </p>

                  <h2>
                    Tutti i film
                  </h2>

                </div>


                <div className="catalog-tools">

                  <input
                    type="text"
                    className="search-bar"
                    placeholder="Cerca film, genere o regista..."
                    value={search}
                    onChange={e =>
                      setSearch(e.target.value)
                    }
                  />

                  <span className="result-count">
                    {filteredFilms.length} risultati
                  </span>

                </div>

              </div>


              {filteredFilms.length === 0 && (

                <p className="empty-message">
                  Nessun film trovato.
                </p>

              )}


              <div className="movie-grid">

                {filteredFilms.map(film => (

                  <button
                    className="movie-card"
                    key={film.id}
                    onClick={() =>
                      showFilm(film)
                    }
                  >

                    <div className="movie-card-header">

                      <span className="movie-year">
                        {film.year}
                      </span>

                      <span className="movie-duration">
                        {film.duration} min
                      </span>

                    </div>


                    <h3>
                      {film.title}
                    </h3>


                    <p className="movie-genre">
                      {film.genre}
                    </p>


                    <div className="movie-meta">

                      <span>
                        {film.countryProduction}
                      </span>


                      {film.registaId && (

                        <span>
                          {film.registaNome}{' '}
                          {film.registaCognome}
                        </span>

                      )}

                    </div>

                  </button>

                ))}

              </div>

            </section>

          </>

        )}



      {/* DETTAGLIO FESTIVAL */}

      {selectedFestival &&
        !selectedFilm &&
        !detailsLoading && (

          <section className="detail-page">


            <div className="detail-title-row">

              <div>

                <p className="section-label">
                  FESTIVAL
                </p>

                <h2>
                  {selectedFestival.nome}
                </h2>

              </div>


              <span className="detail-year">
                {selectedFestival.anno}
              </span>

            </div>



            <div className="festival-detail-grid">


              {/* INFORMAZIONI FESTIVAL */}

              <article className="panel">

                <p className="panel-label">
                  INFORMAZIONI
                </p>


                <p>

                  <strong>
                    Città
                  </strong>

                  <span>
                    {selectedFestival.citta}
                  </span>

                </p>


                <p>

                  <strong>
                    Dal
                  </strong>

                  <span>
                    {formatDate(
                      selectedFestival.dataInizio
                    )}
                  </span>

                </p>


                <p>

                  <strong>
                    Al
                  </strong>

                  <span>
                    {formatDate(
                      selectedFestival.dataFine
                    )}
                  </span>

                </p>


                <div className="description-box">
                  {selectedFestival.descrizione}
                </div>

              </article>



              {/* PROGRAMMA */}

              <article className="panel">

                <p className="panel-label">
                  PROGRAMMA
                </p>


                {screenings.length === 0 && (

                  <p className="empty-message">
                    Nessuna proiezione programmata.
                  </p>

                )}


                <div className="screening-list">

                  {screenings.map(screening => (

                    <div
                      className="screening-item"
                      key={screening.id}
                    >

                      <div>

                        <strong>
                          {screening.filmTitle}
                        </strong>

                        <span>

                          {formatDate(
                            screening.data
                          )}

                          {' · '}

                          {screening.ora}

                        </span>

                      </div>


                      <div className="screening-right">

                        <span>
                          {screening.salaNome}
                        </span>

                        <small>
                          {screening.stato}
                        </small>

                      </div>

                    </div>

                  ))}

                </div>

              </article>

            </div>



            {/* FILM DEL FESTIVAL */}

            <section className="section-block compact-section">

              <div className="section-header">

                <div>

                  <p className="section-label">
                    PROGRAMMA
                  </p>

                  <h2>
                    Film del festival
                  </h2>

                </div>

              </div>


              {festivalMovies.length === 0 && (

                <p className="empty-message">
                  Nessun film associato.
                </p>

              )}


              <div className="movie-grid">

                {festivalMovies.map(film => (

                  <button
                    className="movie-card"
                    key={film.id}
                    onClick={() =>
                      showFilm(film)
                    }
                  >

                    <div className="movie-card-header">

                      <span className="movie-year">
                        {film.year}
                      </span>

                      <span className="movie-duration">
                        {film.duration} min
                      </span>

                    </div>


                    <h3>
                      {film.title}
                    </h3>


                    <p className="movie-genre">
                      {film.genre}
                    </p>


                    {film.registaId && (

                      <div className="movie-meta">

                        <span>
                          {film.registaNome}{' '}
                          {film.registaCognome}
                        </span>

                      </div>

                    )}

                  </button>

                ))}

              </div>

            </section>

          </section>

        )}



      {/* DETTAGLIO FILM */}

      {selectedFilm &&
        !detailsLoading && (

          <section className="detail-page">


            <div className="detail-title-row">

              <div>

                <p className="section-label">
                  FILM
                </p>

                <h2>
                  {selectedFilm.title}
                </h2>

              </div>


              <span className="detail-year">
                {selectedFilm.year}
              </span>

            </div>



            <div className="film-detail-grid">


              {/* SCHEDA FILM */}

              <article className="panel">

                <p className="panel-label">
                  SCHEDA FILM
                </p>


                <p>

                  <strong>
                    Anno
                  </strong>

                  <span>
                    {selectedFilm.year}
                  </span>

                </p>


                <p>

                  <strong>
                    Durata
                  </strong>

                  <span>
                    {selectedFilm.duration} minuti
                  </span>

                </p>


                <p>

                  <strong>
                    Genere
                  </strong>

                  <span>
                    {selectedFilm.genre}
                  </span>

                </p>


                <p>

                  <strong>
                    Paese
                  </strong>

                  <span>
                    {selectedFilm.countryProduction}
                  </span>

                </p>

              </article>



              {/* REGISTA */}

              <article className="panel">

                <p className="panel-label">
                  REGISTA
                </p>


                {selectedFilm.registaId ? (

                  <>

                    <p>

                      <strong>
                        Nome
                      </strong>

                      <span>

                        {selectedFilm.registaNome}{' '}

                        {selectedFilm.registaCognome}

                      </span>

                    </p>


                    <p>

                      <strong>
                        Data di nascita
                      </strong>

                      <span>

                        {formatDate(
                          selectedFilm.registaDataNascita
                        )}

                      </span>

                    </p>


                    <p>

                      <strong>
                        Nazionalità
                      </strong>

                      <span>
                        {selectedFilm.registaNazionalita}
                      </span>

                    </p>

                  </>

                ) : (

                  <p className="empty-message">
                    Regista non specificato.
                  </p>

                )}

              </article>

            </div>



            {/* RECENSIONI */}

            <section className="section-block compact-section">

              <div className="section-header">

                <div>

                  <p className="section-label">
                    OPINIONI
                  </p>

                  <h2>
                    Recensioni
                  </h2>

                </div>


                <span className="result-count">
                  {reviews.length}
                </span>

              </div>


              {reviews.length === 0 && (

                <p className="empty-message">
                  Ancora nessuna recensione.
                </p>

              )}


              <div className="review-grid">

                {reviews.map(review => (

                  <article
                    className="review-card"
                    key={review.id}
                  >

                    <div className="review-top">

                      <strong>
                        {review.username}
                      </strong>

                      <span className="rating">
                        {review.voto}/10
                      </span>

                    </div>


                    <p>
                      {review.testo}
                    </p>


                    <small>
                      {formatDate(review.data)}
                    </small>

                  </article>

                ))}

              </div>

            </section>

          </section>

        )}

    </main>
  )
}

export default App