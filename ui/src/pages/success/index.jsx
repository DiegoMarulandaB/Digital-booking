import { Button } from "../../components/Button"
import { useNavigate } from 'react-router-dom';
import './Success.css'

export const Success = () => {
  const navigate = useNavigate();

  const handleReserveClick = () => {
    navigate('/');
  };
  return (
    <section className="success__container">
      <h1>¡Registro Exitoso!</h1>
      <p>Su cuenta ha sido validada con éxito.</p>
      <p>¡Bienvenido!</p>
      <p>Aprovecha al máximo tus próximas vacaciones.</p>
      <p>Explora nuestros emocionantes planes turísticos y reserva ahora mismo.</p>
      <Button onClick={handleReserveClick}>Reservar ahora</Button>
    </section>
  )
}
