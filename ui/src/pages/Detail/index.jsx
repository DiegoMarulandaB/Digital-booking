import { Link, useParams } from "react-router-dom"
import { useGlobalState } from "../../context"
import { Gallery } from "../../components/Gallery"
import { Feature } from "../../components/Feature"
import { BsFillArrowLeftCircleFill } from "react-icons/bs"
import { Politics } from "../../components/Politics"
import "./detail.css"
import { Container } from "../../components/Container"
import { LocateProduct, SearchProduct } from "../../components/SearchProduct"
import { DatesPicker } from "../../components/DatesPicker"
import { useEffect } from "react"

export const dataGallery = {
  galleryImage: [
    "https://images.unsplash.com/photo-1602385676570-ca41945dcf69?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
    "https://images.unsplash.com/photo-1617284562844-a2098e406b8e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80",
    "https://images.unsplash.com/photo-1507125524815-d9d6dccda1dc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=782&q=80",
    "https://images.unsplash.com/photo-1587019720353-0ac8b27083fd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
    "https://images.unsplash.com/photo-1614444894791-c0c4d4286c35?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
  ],
}
export const feature = [
  "mono",
  "computadora",
  "serpiente",
  "escalar",
  "mono",
  "computadora",
  "serpiente",
  "escalar",
]

const Detail = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  let { id } = useParams()
  const { state } = useGlobalState()
  const { categories, tours } = state

  const itemDetail = tours?.find((item) => item.id == id)
  const getCategoryName = (categoryId) => {
    const category = categories?.find((cat) => cat.id === categoryId)
    return category ? category.categoryName : ""
  }

  const dataCountry = state.countries.find(
    (country) => country.id === itemDetail.countryId
  )
  console.log("busqueda1", state.toursCountryDate)

  return (
    <Container>
      <div className="detail">
        <div>
          <h1 className="detail__title">{itemDetail.tourName}</h1>
        </div>
        <Link className="detail__icon" to="/">
          <BsFillArrowLeftCircleFill />
        </Link>
      </div>
      <SearchProduct country={dataCountry} itemDetail={itemDetail} />
      {/* //Layout component Gallery */}
      <Gallery dataImage={itemDetail?.images} />

      <section className="detail__content">
        <div className="detail__info">
          <div className="detail-value">
            <h3 className="detail__price">{`Precio: ${itemDetail.tourPrice} USD`}</h3>
            <p className="detail__category">
              {getCategoryName(itemDetail.categoryId)}
            </p>
          </div>
          <p className="detail__description">{itemDetail.tourDescription}</p>
        </div>
      </section>
      <Feature feature={itemDetail?.features} />
      <DatesPicker tour={itemDetail} searchDate={state.toursCountryDate} />
      <LocateProduct country={dataCountry} itemDetail={itemDetail} />
      <Politics />
    </Container>
  )
}

export default Detail
