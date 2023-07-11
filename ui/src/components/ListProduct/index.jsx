import { useState } from 'react';
import { useGlobalState } from "../../context";
import { actions } from "../../context/reducer";
import { Pagination } from '../Pagination';
import { Button } from '../Button';
import { usePagination } from '../../hooks/usePagination';
import { ModalProduct } from '../Modal/ModalProduct';
import { useForm } from 'react-hook-form';
import { AiFillDelete, AiFillEdit } from 'react-icons/ai';
import { GrAdd } from 'react-icons/gr';
import Swal from "sweetalert2";
import './ListProduct.css';

export const ListProduct = () => {
  const { state, dispatch, deleteTour, updateTour, addTour } = useGlobalState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedTour, setSelectedTour] = useState(null);
  const { currentPage, goToNextPage, goToPrevPage, getCurrentPageItems, getTotalPages } = usePagination(6);

  const tours = state?.tours || [];
  const categories = state?.categories || [];

  const { reset, watch } = useForm();
  const [editMode, setEditMode] = useState(false);
  const [tourForm, setTourForm] = useState({
    id: '',
    categoryId: 0,
    countryId: '',
    features: [],
    images: [],
    tourCapacity: 0,
    tourClassification: "",
    tourDescription: "",
    tourName: '',
    tourPrice: 0,
    tourScore: 0
  });

  const openModal = (tour) => {
    if (tour) {
      setEditMode(true);
      setTourForm(tour);
    } else {
      setEditMode(false);
      setTourForm({
        id: '',
        categoryId: 0,
        countryId: '',
        features: [],
        images: [],
        tourCapacity: 0,
        tourClassification: "",
        tourDescription: "",
        tourName: '',
        tourPrice: 0,
        tourScore: 0
      });
      reset();
    }
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedTour(null);
    setIsModalOpen(false);
  };

  const handleDeleteTour = async (tourId) => {
    try {
      const result = await Swal.fire({
        title: 'Estas seguro que desea eliminar un tour.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#6D9886',
        cancelButtonColor: '#ED2B2A',
        confirmButtonText: 'Ok'
      });

      if (result.isConfirmed) {
        await deleteTour(tourId);
        Swal.fire({
          title: 'El Tour ha sido eliminado.',
          icon: 'success',
          confirmButtonColor: '#6D9886',
          confirmButtonText: 'Ok'
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.close();
      }
    } catch (error) {
      console.error("Error al borrar tour:", error);
      Swal.fire("Error", "Se ha producido un error al eliminar el tour.", "error");
    }
  };

  const totalPages = getTotalPages(tours);
  const currentTours = getCurrentPageItems(tours);

  const handleFormSubmit = async (data) => {
    const newfeatures = watch("features")
    console.log("hola", newfeatures)
    data.categoryId = parseInt(data.categoryId);
    data.countryId = parseInt(data.countryId);
    const dataF = {
      tourClassification: "Bueno",
      tourAvailability: 0,
      earliestCheckInHour: "10:00:00",
      latestCheckInHour: "12:00:00",
      featuresId: [1, 2, 3]
    }
    try {
      const updatedTour = { ...tourForm, ...data, ...dataF, features: newfeatures };
      console.log("ADDTOUR =>", updatedTour)

      if (editMode) {
        await updateTour(updatedTour.id, updatedTour);
        Swal.fire({
          title: 'Tour actualizado correctamente.',
          icon: 'success',
          confirmButtonColor: '#6D9886',
          confirmButtonText: 'Ok'
        });
        dispatch({
          type: actions.UPDATE_CATEGORY,
          payload: updatedTour,
        });
      } else {
        await addTour(updatedTour);
        Swal.fire({
          title: 'Tour agregado correctamente.',
          icon: 'success',
          confirmButtonColor: '#6D9886',
          confirmButtonText: 'Ok'
        });
      }
      closeModal();
      reset();

      window.location.reload();
    } catch (error) {
      console.error("Error adding/updating tour:", error);
      Swal.fire("Error", "Se ha producido un error al guardar el tour.", "error");
    }
  };

  const getCategoryName = (categoryId) => {
    const category = categories.find((cat) => cat.id === categoryId);
    return category ? category.categoryName : '';
  };

  return (
    <section className="list__container">
      <Button onClick={() => openModal(null)}><GrAdd /></Button>
      {currentTours.map((tour) => (
        <article className="list__content" key={tour.id}>
          <img className="list__image" src={tour.images[0]?.imageUrl} alt="" />
          <p className="list__title">{tour.tourName}</p>
          <p>{getCategoryName(tour.categoryId)}</p>
          <div className='list__button'>
            <Button onClick={() => openModal(tour)}><AiFillEdit /></Button>
            <Button type="primary" onClick={() => handleDeleteTour(tour.id)}><AiFillDelete /></Button>
          </div>
        </article>
      ))}
      {isModalOpen && (
        <ModalProduct
          tour={selectedTour}
          onClose={closeModal}
          editMode={editMode}
          handleFormSubmit={handleFormSubmit}
          tourForm={tourForm}
          onSubmit={handleFormSubmit}
        />
      )}
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onNextPage={goToNextPage}
        onPrevPage={goToPrevPage}
      />
    </section>
  );
};