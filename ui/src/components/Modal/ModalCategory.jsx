import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { Button } from "../Button";
import { GrFormClose } from "react-icons/gr";
import "./Modal.css";

export const ModalCategory = ({ onClose, editMode, categorie, handleFormSubmit, categoryForm }) => {
  const { register, handleSubmit, setValue, watch } = useForm();
  const categoryImageFile = watch('categoryImageFile');

  useEffect(() => {
    if (editMode && categoryForm) {
      setValue('categoryName', categoryForm.categoryName);
      setValue('categoryImageURL', categoryForm.imageCategory.imageUrl);
      setValue('categoryDescription', categoryForm.categoryDescription);
    } else {
      setValue('categoryName', '');
      setValue('categoryImageURL', '');
      setValue('categoryDescription', '');
    }
  }, [editMode, categoryForm, setValue]);

  useEffect(() => {
    if (categoryImageFile instanceof File || categoryImageFile instanceof Blob) {
      const imageUrl = URL.createObjectURL(categoryImageFile);
      setValue('categoryImageURL', imageUrl);
    }
  }, [categoryImageFile, setValue]);

  const [images, setimages] = useState([]);
  const changeInput = (e) => {
    let indexImg;

    if (images.length > 0) {
      indexImg = images[images.length - 1].index + 1;
    } else {
      indexImg = 0;
    }

    let newImgsToState = readmultifiles(e, indexImg);
    let newImgsState = [...images, ...newImgsToState];
    setimages(newImgsState);
  };

  function readmultifiles(e, indexInicial) {
    const files = e.currentTarget.files;
    const arrayImages = [];

    Object.keys(files).forEach((i) => {
      const file = files[i];

      let url = URL.createObjectURL(file);
      arrayImages.push({
        index: indexInicial,
        name: file.name,
        url,
        file
      });

      indexInicial++;
    });

    return arrayImages;
  }

  function deleteImg(indice) {
    const newImgs = images.filter(function (element) {
      return element.index !== indice;
    });
    setimages(newImgs);
  }

  return (
    <section className="modal__overlay">
      <div className="modal__content">
        <h3>{editMode ? 'Editar Categoría' : 'Agregar Categoría'}</h3>
        {
          editMode ? <img className='modal__image' src={categoryForm?.imageCategory.imageUrl} alt="" /> : ""
        }
        <form className="modal__form" onSubmit={handleSubmit(handleFormSubmit)}>
          <label htmlFor="categoryName">Nombre de la categoría:</label>
          <input
            type="text"
            id="categoryName"
            placeholder="Nombre de la categoría"
            defaultValue={categorie?.categoryName || ''}
            {...register('categoryName')}
          />
          {editMode ? "" : (
            <>
              <label htmlFor="categoryImageFile">Cargar imagen:</label>
              <input
                type="file"
                id="categoryImageFile"
                accept="image/jpeg, image/png"
                {...register('categoryImageFile')}
                onChange={changeInput}
              />
            </>
          )}
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
                  />
                </div>
              </div>
            ))}
          </div>

          <label htmlFor="categoryDescription">Descripción:</label>
          <textarea
            type="text"
            id="categoryDescription"
            rows="5"
            placeholder="Descripción"
            defaultValue={categorie?.categoryDescription
              || ''}
            {...register('categoryDescription')}
          />
          <Button type="submit">{editMode ? 'Guardar' : 'Agregar'}</Button>
        </form>
        <span className="modal__close" onClick={onClose}><GrFormClose /></span>
      </div>
    </section>
  )
}
