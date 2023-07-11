import { BsStarFill, BsStar, BsStarHalf } from "react-icons/bs"
import { MdOutlineLocationOn } from "react-icons/md"
import { Fragment, useEffect, useState } from "react"
import { AiOutlineShareAlt } from "react-icons/ai"
import L from "leaflet"

import "leaflet/dist/leaflet.css"
import "./SearchProduct.css"
import { ModalShareProduct } from "../Modal/ModalShareProduct"

export const SearchProduct = ({ itemDetail, country }) => {
  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleShare = () => {
    setIsModalOpen(true)
  }

  //score
  const score = {
    Malo: 3,
    Aceptable: 5,
    Satisfactorio: 6,
    Bueno: 8,
    MuyBueno: 9,
    Excelente: 10
  };

  function getScore(classification) {
    if (Object.prototype.hasOwnProperty.call(score, classification)) {
      return score[classification];
    } else {
      return "Invalid classification";
    }
  }

  return (
    <Fragment>
      <ModalShareProduct
        isModalOpen={isModalOpen}
        setIsModalOpen={setIsModalOpen}
        itemDetail={itemDetail}
      />

      <div className="detail__rating">
        <h3 className="detail__rating-title">
          <MdOutlineLocationOn /> {country?.countryName}
          <AiOutlineShareAlt onClick={handleShare} />
        </h3>
        <div className="detail__rating-container">
          <div className="detail__rating-content">
            <p className="detail__rating-classification">
              {itemDetail?.tourClassification}
            </p>
            <p className="detail__rating-star">
              <BsStarFill />
              <BsStarFill />
              <BsStarFill />
              <BsStarHalf />
              <BsStar />
            </p>
          </div>
          <p className="detail__rating-score">{getScore(itemDetail?.tourClassification)}</p>
        </div>
      </div>
    </Fragment>
  )
}

export const LocateProduct = ({ country, itemDetail }) => {
  useEffect(() => {
    const mapContainer = L.DomUtil.get("map")
    if (mapContainer != null) {
      mapContainer._leaflet_id = null
    }
    const osm = L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
      maxZoom: 19,
      attribution: "© OpenStreetMap",
    })

    const customIcon = L.icon({
      iconUrl: 'https://www.iconpacks.net/icons/2/free-camping-location-icon-2958-thumb.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
    });

    const countryMarker = L.marker([country.latitude, country.longitude], { icon: customIcon })
      .bindPopup(
        `<b>${itemDetail.tourName}</b><br>${itemDetail.tourDescription}`
      )
      .openPopup()
    const map = L.map("map", {
      center: [country.latitude, country.longitude],
      zoom: 2,
      layers: [osm, countryMarker],
    })
    return () => {
      map.remove()
    }
  }, [country])

  return (
    <section className="locate">
      <h2 className="locate__title">Ubicación tour</h2>
      <div className="locate__content">
        <div id="map" className="map-container"></div>
      </div>
    </section>
  )
}
