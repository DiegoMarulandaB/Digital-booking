import { Button } from "../../components/Button";
import { useNavigate } from 'react-router-dom';
import './Success.css';
import { ImAirplane } from 'react-icons/im';


export const BookingSuccessful = () => {
  const navigate = useNavigate();

  const handleReserveClick = () => {
    navigate('/');
  };

  return (
    <div className="form__container">
    <div className="success__container">
      <h1>¡Reserva Exitosa!</h1>
      <ImAirplane className="airplane-icon" />
        <p>Su reserva ha sido realizada con <span className="text__container">éxito</span>.</p>
        <p>¡Tu <span className="text__container" >destino</span> espera por ti!</p>
        <p>La <span className="text__container">aventura</span> apenas comienza.</p>
      <Button onClick={handleReserveClick}>Ok</Button>
    </div>
    </div>
  );
};
