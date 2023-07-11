import { useState } from "react";
import './Carrusel.css'


export const Carrusel = ({ dataImage }) => {
  const [activeSlide, setActiveSlide] = useState(0);
  const handleNextSlide = () => {
    setActiveSlide((prevSlide) => (prevSlide === dataImage?.length - 1 ? 0 : prevSlide + 1));
  };

  const handlePreviousSlide = () => {
    setActiveSlide((prevSlide) => (prevSlide === 0 ? dataImage?.length - 1 : prevSlide - 1));
  };

  return (
    <section className="slider">
      <div className="slider__slide active" id={`img_${activeSlide + 1}`}>
        <img src={dataImage[activeSlide]?.imageUrl} alt={`Image ${activeSlide + 1}`} />
      </div>
      <div className="slider__controls">
        <button onClick={handlePreviousSlide}>{'<'}</button>
        <button onClick={handleNextSlide}>{'>'}</button>
      </div>
      <p className='slider__counter'>{`${activeSlide + 1}/${dataImage.length}`}</p>
    </section>
  )
}
