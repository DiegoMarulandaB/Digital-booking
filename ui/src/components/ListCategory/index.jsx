import { useState } from 'react';
import { Button } from '../Button';
import { useGlobalState } from '../../context';
import { Pagination } from '../Pagination';
import { actions } from '../../context/reducer';
import { useForm } from 'react-hook-form';
import { usePagination } from '../../hooks/usePagination';
import { ModalCategory } from '../Modal/ModalCategory';
import { AiFillDelete, AiFillEdit } from 'react-icons/ai';
//import { AiFillDelete } from 'react-icons/ai';
import { GrAdd } from 'react-icons/gr';
import Swal from "sweetalert2";
import './ListCategory.css'

export const ListCategory = () => {

  const { state, dispatch, deleteCategory, updateCategory, addCategory } = useGlobalState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCategorie, setSelectedCategorie] = useState(null);
  const { currentPage, goToNextPage, goToPrevPage, getCurrentPageItems, getTotalPages } = usePagination(6);

  const categories = state?.categories || []

  const { reset } = useForm();
  const [editMode, setEditMode] = useState(false);
  const [categoryForm, setCategoryForm] = useState({
    id: '',
    categoryName: '',
    categoryImageURL: '',
    categoryDescription: ''
  });

  const openModal = (categorie) => {
    if (categorie) {
      setEditMode(true);
      setCategoryForm(categorie);
    } else {
      setEditMode(false);
      setCategoryForm({
        id: '',
        categoryName: '',
        categoryImageURL: '',
        categoryDescription: ''

      });
      reset();
    }
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedCategorie(null);
    setIsModalOpen(false);
  };

  const handleDeleteCategorie = async (categoryId) => {
    try {
      const result = await Swal.fire({
        title: 'Estas seguro que desea eliminar una categoría.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#6D9886',
        cancelButtonColor: '#ED2B2A',
        confirmButtonText: 'Ok'
      });

      if (result.isConfirmed) {
        await deleteCategory(categoryId);
        Swal.fire({
          title: 'Tu categoría ha sido eliminada.',
          icon: 'success',
          confirmButtonColor: '#6D9886',
          confirmButtonText: 'Ok'
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.close();
      }
    } catch (error) {
      console.error("Error al borrar categoría:", error);
      Swal.fire("Error", "Se ha producido un error al eliminar la categoría.", "error");
    }
  };

  const totalPages = getTotalPages(categories);
  const currentCategories = getCurrentPageItems(categories);

  const handleFormSubmit = async (data) => {
    try {
      const updatedCategory = { ...categoryForm, ...data };
      if (editMode) {
        await updateCategory(updatedCategory.id, updatedCategory);
        dispatch({
          type: actions.UPDATE_CATEGORY,
          payload: updatedCategory,
        });
        Swal.fire({
          title: "Categoría actualizada",
          confirmButtonColor: '#6D9886',
          icon: "success"
        }).then(() => {
          window.location.reload();
        });
      } else {
        await addCategory(updatedCategory);
        Swal.fire({
          title: "Categoría añadida",
          confirmButtonColor: '#6D9886',
          icon: "success"
        }).then(() => {
          window.location.reload();
        });
      }
      closeModal();
      reset();
    } catch (error) {
      console.error("Error adding/updating category:", error);
      Swal.fire(
        "Error",
        "Se ha producido un error al añadir/actualizar la categoría.",
        "error"
      );
    }
  };

  return (
    <section className="list__container">
      <Button onClick={() => openModal(null)}><GrAdd /></Button>
      {currentCategories.map((categorie) => (
        <article className="list__content" key={categorie?.id}>
          <img className="list__image" src={categorie?.imageCategory?.imageUrl} alt="" />
          <p className="list__title">{categorie?.categoryName}</p>
          <div className='list__button'>
            <Button onClick={() => openModal(categorie)}><AiFillEdit /></Button>
            <Button type="primary" onClick={() => handleDeleteCategorie(categorie?.id)}><AiFillDelete /></Button>
          </div>
        </article>
      ))}
      {isModalOpen && (
        <ModalCategory
          categorie={selectedCategorie}
          onClose={closeModal}
          editMode={editMode}
          handleFormSubmit={handleFormSubmit}
          categoryForm={categoryForm}
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

