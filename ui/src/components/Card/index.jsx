import { AiOutlineHeart } from "react-icons/ai"
import { Button } from "../Button"
import "./Card.css"
import "./SimpleCard.css"
import { useNavigate } from "react-router-dom"
import { useGlobalState } from "../../context"

/**
 * Representa una tarjeta simple con una imagen y un título.
 * @param {string} title - El título de la tarjeta.
 * @param {string} imageSrc - La URL de la imagen de la tarjeta.
 */
export const SimpleCard = ({ title, imageSrc, description }) => {
  return (
    <figure className="simpleCard">
      <img src={imageSrc} alt={title} className="simpleCard__image" />
      <figcaption className="simpleCard__container">
        <h4 className="simpleCard__title">{title}</h4>
        <p className="simpleCard__description">{description}</p>
      </figcaption>
    </figure>
  )
}

/**
 * Representa una tarjeta detallada con una imagen, título, descripción, categoría y puntaje.
 * @param {string} imageSrc - La URL de la imagen de la tarjeta.
 * @param {string} title - El título de la tarjeta.
 * @param {number} rating - La calificación de la tarjeta.
 * @param {string} description - La descripción de la tarjeta.
 * @param {string} category - La categoría de la tarjeta.
 * @param {string} classification - La clasificación de la tarjeta.
 * @param {number} score - El puntaje de la tarjeta.
 */
export const DetailedCard = ({
  id,
  imageSrc,
  title,
  rating,
  description,
  category,
  classification,
  score,
}) => {
  const navigate = useNavigate()
  const { addFav, user } = useGlobalState()

  return (
    <figure className="card">
      <img src={imageSrc} alt={title} className="card__image" />
      <figcaption className="card__content">
        <span className="card__category">{category}</span>
        <div className="card__details">
          {user && (
            <AiOutlineHeart
              className="card__favorite"
              onClick={() =>
                addFav({
                  id,
                  imageSrc,
                  title,
                  rating,
                  description,
                  category,
                  classification,
                  score,
                })
              }
            />
          )}

          <span className="card__rating">{rating}</span>
          <div className="card__info">
            <p className="card__score">{score}</p>
            <p className="card__classification">{classification}</p>
          </div>
        </div>
        <div className="card__info">
          <h4 className="card__title">{title}</h4>
          <p className="card__description">{description}</p>
          <Button type="primary" onClick={() => navigate(`/detalle/${id}`)}>
            Ver más
          </Button>
        </div>
      </figcaption>
    </figure>
  )
}
