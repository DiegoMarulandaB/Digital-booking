import { FaFacebookSquare, FaTiktok } from "react-icons/fa"
import { IoLogoWhatsapp } from "react-icons/io"
import { AiFillInstagram, AiOutlineShareAlt } from "react-icons/ai"
import "./footer.css"
import { Fragment, useState } from "react"
import { ModalShare } from "../Modal/ModalShare"

const Footer = () => {
  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleShare = () => {
    setIsModalOpen(true)
  }

  return (
    <Fragment>
      <footer className="footer">
        <div className="footer__content">
          <a rel="noreferrer" target="_blank">
            <img
              className="footer_logo"
              src="https://c3-e10-digitalbooking-imagenes.s3.us-east-2.amazonaws.com/footersvg-ft.svg"
              alt="isologodigitalbooking"
            />
          </a>
          <p>2023 © Digital Booking</p>
        </div>
        <div className="social-icons">
          <AiOutlineShareAlt onClick={handleShare} />

          <a
            className="social-media"
            href="https://www.facebook.com"
            rel="noreferrer"
            target="_blank"
          >
            <FaFacebookSquare />
          </a>
          <a
            className="social-media"
            href="https://www.instagram.com"
            rel="noreferrer"
            target="_blank"
          >
            <AiFillInstagram />
          </a>
          <a
            className="social-media"
            href="https://www.tiktok.com"
            rel="noreferrer"
            target="_blank"
          >
            <FaTiktok />
          </a>
          <a
            className="social-media"
            href="https://www.whatsapp.com"
            rel="noreferrer"
            target="_blank"
          >
            <IoLogoWhatsapp />
          </a>
        </div>
      </footer>
      <ModalShare isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} />
    </Fragment>
  )
}

export default Footer
