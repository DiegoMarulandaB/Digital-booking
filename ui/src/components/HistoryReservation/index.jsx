import { useState, useEffect } from "react"
import "./HistoryReservation.css"
import { useGlobalState } from "../../context"

export const HistoryReservation = () => {
  const [reservations, setReservations] = useState([])
  const { user, fetchReservations, state } = useGlobalState()

  useEffect(() => {
    const getReservations = async () => {
      if (user) {
        const data = await fetchReservations(user.id)
        setReservations(data.data)
      }
    }
    getReservations()
  }, [])

  const getTourName = (tourId) => {
    if (state?.tours && state?.tours.length > 0) {
      const reservation = state.tours.find((item) => item.id == tourId);
      return reservation ? reservation.tourName : "";
    }
    return "";
  };

  return (
    <section className="reservations__content">
      <h1>Mis Reservas</h1>
      {reservations?.length === 0 ? (
        <h3>Buscando reservas</h3>
      ) : (
        <table id="reservationsTable">
          <thead>
            <tr>
              <th>Tour</th>
              <th>Fecha Inicio</th>
              <th>Fecha Final</th>
              <th>Hora - checkin (24h)</th>
            </tr>
          </thead>
          <tbody>
            {reservations?.map((reservation) => (
              <tr key={reservation?.id}>
                <td>{getTourName(reservation?.idTour)}</td>
                <td>{reservation?.initialDate}</td>
                <td>{reservation?.finalDate}</td>
                <td>{reservation?.startTime}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  )
}
