import { useEffect, useState } from "react";
import { fetchEvents } from "../services/eventsService";

export default function Events() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    fetchEvents().then(setEvents).catch(console.error);
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Eventos</h1>
      <ul>
        {events.map((event: any, index: number) => (
          <li key={index} className="mb-2 p-2 border rounded">
            {JSON.stringify(event)}
          </li>
        ))}
      </ul>
    </div>
  );
}
