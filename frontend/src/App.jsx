import { useEffect, useState } from "react";
import "./App.css";

const API_URL = "https://crewvane-production.up.railway.app/api/events";

function App() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(API_URL)
        .then((response) => {
          if (!response.ok) throw new Error("Failed to fetch events");
          return response.json();
        })
        .then((data) => setEvents(data))
        .catch((error) => console.error(error))
        .finally(() => setLoading(false));
  }, []);

  return (
      <div className="app">
        <nav className="navbar">
          <h2>Crewvane</h2>
          <span>Campus Connect</span>
        </nav>

        <main className="container">
          <h1>Discover Campus Events</h1>
          <p>Find what's happening around your campus.</p>

          <h2>Upcoming Events</h2>

          {loading ? (
              <p>Loading events...</p>
          ) : events.length === 0 ? (
              <p>No events available yet.</p>
          ) : (
              <div className="event-grid">
                {events.map((event) => (
                    <div className="event-card" key={event.id}>
                      <h3>{event.title}</h3>
                      <p>{event.description}</p>
                      <span>📍 {event.location}</span>
                    </div>
                ))}
              </div>
          )}
        </main>
      </div>
  );
}

export default App;