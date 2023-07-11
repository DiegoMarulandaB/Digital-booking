import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { Button } from "../Button"
import { useGlobalState } from "../../context"
import { GrFormClose } from "react-icons/gr"
import "./Modal.css"

export const ModalProduct = ({
  onClose,
  editMode,
  tour,
  handleFormSubmit,
  tourForm,
}) => {
  const { register, handleSubmit, setValue, watch } = useForm()

  const { state } = useGlobalState()
  const categories = state?.categories || []
  const countries = state?.countries || []
  const toursImageFile = watch("toursImageFile")

  useEffect(() => {
    if (editMode && tourForm) {
      setValue("tourName", tourForm.tourName)
      setValue("image_url", tourForm.image_url)
      setValue("tourDescription", tourForm.tourDescription)
      setValue("categoryId", tourForm?.categoryId)
      setValue("tourPrice", tourForm?.tourPrice)
      setValue("tourCapacity", tourForm?.tourCapacity)
      setValue("countryId", tourForm?.countryId)
      setValue("features", tourForm?.features || [])
    } else {
      setValue("tourName", "")
      setValue("image_url", "")
      setValue("tourDescription", "")
      setValue("categoryId", "")
      setValue("featuresId", [])
      setValue("country", "")
      setValue("tourPrice", "")
      setValue("tourCapacity", "")
    }
  }, [editMode, tourForm, setValue])

  useEffect(() => {
    if (toursImageFile instanceof File || toursImageFile instanceof Blob) {
      const imageUrl = URL.createObjectURL(toursImageFile)
      setValue("categoryImageURL", imageUrl)
    }
  }, [toursImageFile, setValue])

  const [images, setimages] = useState([])
  const changeInput = (e) => {
    let indexImg

    if (images.length > 0) {
      indexImg = images[images.length - 1].index + 1
    } else {
      indexImg = 0
    }

    let newImgsToState = readmultifiles(e, indexImg)
    let newImgsState = [...images, ...newImgsToState]
    setimages(newImgsState)
  }

  function readmultifiles(e, indexInicial) {
    const files = e.currentTarget.files
    const arrayImages = []

    Object.keys(files).forEach((i) => {
      const file = files[i]

      let url = URL.createObjectURL(file)
      arrayImages.push({
        index: indexInicial,
        name: file.name,
        url,
        file,
      })

      indexInicial++
    })

    return arrayImages
  }
  function deleteImg(indice) {
    const newImgs = images.filter(function (element) {
      return element.index !== indice
    })
    console.log(newImgs)
    setimages(newImgs)
  }

  console.log("state", state.features, tourForm?.features)

  return (
    <section className="modal__overlay">
      <div className="modal__content">
        <h3>{editMode ? "Editar Tour" : "Agregar Tour"}</h3>
        {editMode ? (
          <img className="modal__image"
            src={tourForm.images[0].imageUrl}
            alt={`${tourForm.images[0].imageTitle}`} />
        ) : (
          ""
        )}
        <form className="modal__form" onSubmit={handleSubmit(handleFormSubmit)}>
          <label htmlFor="tourName">Nombre del tour:</label>
          <input
            type="text"
            id="tourName"
            placeholder="Nombre de la categoría"
            defaultValue={tour?.tourName || ""}
            {...register("tourName")}
          />
          {
            editMode ? "" : (
              <>
                <label htmlFor="toursImageFile">Cargar imagen:</label>
                <input
                  type="file"
                  id="toursImageFile"
                  multiple
                  accept="image/jpeg, image/png"
                  {...register("toursImageFile")}
                  onChange={changeInput}
                />
              </>
            )
          }
          <div className="image__container">
            {images.map((imagen) => (
              <div className="image__content" key={imagen.index}>
                <div className="content-img">
                  <button
                    className="image-btn"
                    onClick={deleteImg.bind(this, imagen.index)}
                  >
                    <GrFormClose />
                  </button>
                  <img
                    alt="algo"
                    src={imagen.url}
                    data-toggle="modal"
                    data-target="#ModalPreViewImg"
                    className="image-responsive"
                  ></img>
                </div>
              </div>
            ))}
          </div>
          <label htmlFor="description">Descripción:</label>
          <textarea
            type="text"
            id="description"
            rows="5"
            placeholder="Descripción"
            defaultValue={tour?.tourDescription || ""}
            {...register("tourDescription")}
          />
          <label htmlFor="id_category">Categoría:</label>
          <select id="id_category" {...register("categoryId")}>
            {categories.map((category) => (
              <option key={category?.id} value={category?.id}>
                {category?.categoryName}
              </option>
            ))}
          </select>


          {/*           <fieldset className="list__feature">
            <legend>Features</legend>
            {state?.features.map((feature) => {
              const checked = watch("features")?.includes(feature.id);
              return (
                <div className="" key={feature.id}>
                  <input
                    type="checkbox"
                    name={feature.featureName}
                    value={feature.featureName}
                    defaultChecked={checked}
                    onChange={(e) => {
                      const { checked } = e.target;
                      if (checked) {
                        setValue("features", [...watch("features"), feature.id]);
                      } else {
                        setValue(
                          "features",
                          watch("features").filter((id) => id !== feature.id)
                        );
                      }
                    }}
                  />
                  {feature.featureName}
                </div>
              );
            })}
          </fieldset> */}


          <label htmlFor="countryId">País:</label>
          <select id="countryId" {...register("countryId")}>
            {countries.map((country) => (
              <option key={country?.id} value={country?.id}>
                {country?.countryName}
              </option>
            ))}
          </select>
          <label htmlFor="price">Precio:</label>
          <input
            type="number"
            id="price"
            placeholder="Precio"
            defaultValue={tour?.tourPrice || ""}
            {...register("tourPrice")}
          />
          <label htmlFor="capacity">Capacidad:</label>
          <input
            type="number"
            id="capacity"
            placeholder="capacidad"
            defaultValue={tour?.tourCapacity || ""}
            {...register("tourCapacity")}
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
