import { useState } from "react"
import { Button } from "../Button"
import "../ListCategory/ListCategory.css"
import { useGlobalState } from "../../context"
import { Pagination } from "../Pagination"
import { actions } from "../../context/reducer"
import { useForm } from "react-hook-form"
import { usePagination } from "../../hooks/usePagination"
import { ModalCountry } from "../Modal/ModalCountry"
import { AiFillDelete } from "react-icons/ai"
import { GrAdd } from "react-icons/gr"
import { Toast } from "../../utils/Toast"

export const ListCountry = () => {
  const { state, dispatch, createCountry, deleteCountry } = useGlobalState()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedCountry, setSelectedCountry] = useState(null)

  const {
    currentPage,
    goToNextPage,
    goToPrevPage,
    getCurrentPageItems,
    getTotalPages,
  } = usePagination(7)

  const countries = state?.countries || []

  const { reset } = useForm()
  const [editMode, setEditMode] = useState(false)
  const [countryForm, setCountryForm] = useState({
    id: "",
    countryName: "",
    capitalName: "",
    latitude: "",
    longitude: "",
  })

  const openModal = (country) => {
    if (country) {
      setEditMode(true)
      setCountryForm(country)
    } else {
      setEditMode(false)
      setCountryForm({
        id: "",
        countryName: "",
        capitalName: "",
        latitude: "",
        longitude: "",
      })
      reset()
    }
    setIsModalOpen(true)
  }

  const closeModal = () => {
    setSelectedCountry(null)
    setIsModalOpen(false)
  }

  const handleDeleteCountry = async (countryId) => {
    await deleteCountry(countryId)

    dispatch({
      type: actions.REMOVE_COUNTRY,
      payload: countryId,
    })

    if (selectedCountry && selectedCountry.id === countryId) {
      closeModal()
    }
  }

  const totalPages = getTotalPages(countries)
  const currentCountries = getCurrentPageItems(countries)

  const handleFormSubmit = async (data) => {
    const updatedCountry = { ...countryForm, ...data }

    if (editMode) {
      dispatch({
        type: actions.UPDATE_COUNTRY,
        payload: updatedCountry,
      })
    } else {
      await createCountry(data)
      dispatch({
        type: actions.ADD_COUNTRY,
        payload: updatedCountry,
      })
      Toast("Pais agregado", "success")
    }

    closeModal()
    reset()
    window.location.reload()
  }

  return (
    <section className="list__container">
      <Button onClick={() => openModal(null)}>
        <GrAdd />
      </Button>
      {currentCountries.map((country) => (
        <article className="list__content" key={country.id}>
          <img
            className="list__image"
            src={
              "https://upload.wikimedia.org/wikipedia/commons/7/74/Location_icon_from_Noun_Project.png"
            }
            alt=""
          />
          <p className="list__title">{country.countryName}</p>
          <p className="list__title">{country.capitalName}</p>
          <p className="list__title">{country.latitude}</p>
          <p className="list__title">{country.longitude}</p>

          <div className="list__button">
            <Button
              type="primary"
              onClick={() => handleDeleteCountry(country.id)}
            >
              <AiFillDelete />
            </Button>
          </div>
        </article>
      ))}
      {isModalOpen && (
        <ModalCountry
          onClose={closeModal}
          handleFormSubmit={handleFormSubmit}
          countryForm={countryForm}
        />
      )}
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onNextPage={goToNextPage}
        onPrevPage={goToPrevPage}
      />
    </section>
  )
}
