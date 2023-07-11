import { Button } from "../../components/Button";
import { useNavigate } from 'react-router-dom';
import './Failure.css';
import { BiError } from 'react-icons/bi';

export const BookingFailure = () => {
  const navigate = useNavigate();

  const handleReserveClick = () => {
    navigate('/');
  };

  const errorData = {
    field: "nombre",
    errorMessage: "El nombre ingresado es inválido. Asegúrese de utilizar solo letras.",
  };

  return (
    <div className="form__container">
      <div className="failure__container">
        <h1>¡Reserva Fallida!</h1>
        <BiError className="error-icon" />
        <p>Su reserva no ha sido realizada con <span className="text__container">éxito</span>.</p>
        <p>¡Tu <span className="text__container" >destino</span> aún espera por ti!</p>
        <p>{errorData.errorMessage}</p>
        <Button onClick={handleReserveClick}>Ok</Button>
      </div>
    </div>
  );
};


