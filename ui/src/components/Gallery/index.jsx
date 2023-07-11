import { useState } from 'react';
import Modal from 'react-modal';
import { Carrusel } from '../Carrusel'
import './Gallery.css'

export const Gallery = ({ dataImage }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <section className="gallery__container">
        <figure className="gallery__content">
          {
            dataImage?.map((item, i) => (
              <img className='content-image' key={i} src={item?.imageUrl} alt={item?.imageTitle} />

            ))
          }
        </figure>
        <a className='gallery__title' onClick={() => setIsModalOpen(true)}>Ver mas</a>
      </section>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={() => setIsModalOpen(false)}
        contentLabel="Carrusel Modal"
        className="modal"
        overlayClassName="modal__overlay"
      >
        <Carrusel dataImage={dataImage} />
      </Modal>
    </>
  )
}

