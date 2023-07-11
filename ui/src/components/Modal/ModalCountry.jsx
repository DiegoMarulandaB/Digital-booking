import { useEffect, useState, useCallback } from "react"
import { useForm } from "react-hook-form"
import { Button } from "../Button"
import { GrFormClose } from "react-icons/gr"

import "./Modal.css"
import {
  getCountries,
  getCountryCapital,
  getCountryPositions,
} from "../../utils/Country"

export const ModalCountry = ({
  onClose,
  editMode,
  handleFormSubmit,
  countryForm,
}) => {
  const { register, handleSubmit, setValue } = useForm()

  const [allCountries, setAllCountries] = useState([])

  const updateCountryInfo = useCallback(async (selectedCountry) => {
    const capitalName = await getCountryCapital(selectedCountry);
    const location = await getCountryPositions(selectedCountry);
    setValue("capitalName", capitalName.data.data.capital);
    setValue("latitude", location.data.data.lat);
    setValue("longitude", location.data.data.long);
  }, [setValue]);

  useEffect(() => {
    const fetchCountries = async () => {
      const countries = await getCountries()
      setAllCountries(countries.data.data)
    }
    fetchCountries()
  }, [])

  useEffect(() => {
    if (editMode && countryForm) {
      setValue("countryName", countryForm.countryName)
      updateCountryInfo(countryForm.countryName)
    } else {
      setValue("countryName", "")
    }
  }, [editMode, countryForm, setValue, updateCountryInfo])

  return (
    <section className="modal__overlay">
      <div className="modal__content">
        <h3>{editMode ? "Editar País" : "Agregar País"}</h3>
        <form className="modal__form" onSubmit={handleSubmit(handleFormSubmit)}>
          <label>
            Nombre del país:
            <select
              id="countryName"
              name="countryName"
              {...register("countryName", { required: true })}
              onChange={(event) => {
                setValue("countryName", event.target.value)
                updateCountryInfo(event.target.value)
              }}
            >
              {allCountries.map((countryInfo) => (
                <option key={countryInfo.name} value={countryInfo.name}>
                  {countryInfo.name}
                </option>
              ))}
            </select>
          </label>
          <input
            type="text"
            name="capitalName"
            id="capitalName"
            readOnly
            {...register("capitalName")}
            disabled
          />

          <input
            type="text"
            name="latitude"
            id="latitude"
            readOnly
            {...register("latitude")}
            disabled
          />

          <input
            type="text"
            name="longitude"
            id="longitude"
            readOnly
            {...register("longitude")}
            disabled
          />

          <Button type="submit">{editMode ? "Guardar" : "Agregar"}</Button>
        </form>
        <span className="modal__close" onClick={onClose}>
          <GrFormClose />
        </span>
      </div>
    </section>
  )
}
