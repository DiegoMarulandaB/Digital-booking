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

export const ModalShare = ({ isModalOpen, setIsModalOpen }) => {
  const FRONT_URL = "http://bit.ly/digital-booking"
  const MESSAGE =
    "Digital Booking - Vive la aventura, contamos con una gran oferta de experiencia turística únicas 🏞️⛰️🏔️🧗🏽🚵🗻🏜️🌏🏝️🥾🚢🧳✈️"
  const HASH = "#DigitalBooking"
  const TITLE = "Digital Booking - Vive la aventura 🏞️⛰️🏔️🧗🏽🚵🗻🏜️🌏🏝️🥾🚢🧳✈️"

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
            src={
              "https://res.cloudinary.com/karvaroz/image/upload/v1686624348/WhatsApp_Image_2023-06-01_at_7.41.21_PM_ver0im.jpg"
            }
            data-toggle="modal"
            data-target="#ModalPreViewImg"
            className="image-responsive"
          ></img>
        </div>

        <p className="share__message">{MESSAGE}</p>

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
