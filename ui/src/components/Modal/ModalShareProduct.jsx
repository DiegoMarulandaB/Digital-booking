import { GrFormClose } from "react-icons/gr"
import {
  FacebookIcon,
  FacebookShareButton,
  TwitterIcon,
  TwitterShareButton,
  WhatsappIcon,
  WhatsappShareButton,
} from "react-share"
import "./Modal.css"

export const ModalShareProduct = ({
  isModalOpen,
  setIsModalOpen,
  itemDetail,
}) => {
  const FRONT_URL = "http://bit.ly/digital-booking"
  const MESSAGE = itemDetail.tourDescription
  const HASH = "#DigitalBooking"
  const TITLE = `Reserva el ${itemDetail.tourName} en Digital Booking, vive una experiencia única 🏞️⛰️🏔️🧗🏽🚵🗻🏜️🌏🏝️🥾🚢🧳✈️`

  const closeModal = () => {
    setIsModalOpen(false)
  }

  if (!isModalOpen) return null
  return (
    <section className="modal__overlay">
      <div className="modal__content">
        <h3>Compartir</h3>
        <div className="content-img">
          <img
            alt="DigitalBooking"
            src={itemDetail.images[0].imageUrl}
            data-toggle="modal"
            data-target="#ModalPreViewImg"
            className="image-responsive"
          ></img>
        </div>

        <p className="share__message">{itemDetail.tourName}</p>

        <div className="share__icons">
          <FacebookShareButton url={FRONT_URL} quote={TITLE} hashtag={HASH}>
            <FacebookIcon size={32} round />
          </FacebookShareButton>
          <TwitterShareButton
            url={FRONT_URL}
            quote={MESSAGE}
            hashtag={HASH}
            title={TITLE}
          >
            <TwitterIcon size={32} round />
          </TwitterShareButton>
          <WhatsappShareButton
            url={FRONT_URL}
            quote={MESSAGE}
            hashtag={HASH}
            title={TITLE}
          >
            <WhatsappIcon size={32} round />
          </WhatsappShareButton>
        </div>

        <span className="modal__close" onClick={closeModal}>
          <GrFormClose />
        </span>
      </div>
    </section>
  )
}
