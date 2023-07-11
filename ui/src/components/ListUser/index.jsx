import { useEffect, useState } from "react"
import { Button } from "../Button"
import "./ListUser.css"
import { useGlobalState } from "../../context"
import { Pagination } from "../Pagination"
import { actions } from "../../context/reducer"
import { useForm } from "react-hook-form"
import { usePagination } from "../../hooks/usePagination"
import { ModalUser } from "../Modal/ModalUser"
import { AiFillEdit } from "react-icons/ai"

export const ListUser = () => {
  const { state, dispatch, fetchUsers } = useGlobalState()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedUser, setSelectedUser] = useState(null)

  const {
    currentPage,
    goToNextPage,
    goToPrevPage,
    getCurrentPageItems,
    getTotalPages,
  } = usePagination(7)

  useEffect(() => {
    fetchUsers()
  }, [])

  const users = state?.users?.content || []

  const { reset } = useForm()
  const [editMode, setEditMode] = useState(false)
  const [userForm, setUserForm] = useState({
    id: "",
    userName: "",
    role: "",
  })

  const openModal = (user) => {
    if (user) {
      setEditMode(true)
      setUserForm(user)
    } else {
      setEditMode(false)
      setUserForm({
        id: "",
        userName: "",
        role: "",
      })
      reset()
    }
    setIsModalOpen(true)
  }

  const closeModal = () => {
    setSelectedUser(null)
    setIsModalOpen(false)
  }

  const totalPages = getTotalPages(users)
  const currentUsers = getCurrentPageItems(users)

  const handleFormSubmit = (data) => {
    const updatedUser = { ...userForm, ...data }

    if (editMode) {
      dispatch({
        type: actions.UPDATE_USER,
        payload: updatedUser,
      })
    }

    closeModal()
    reset()
  }

  return (
    <section className="list__container">
      {currentUsers.map((user) => (
        <article className="list__content" key={user?.id}>
          <p className="list__title">
            {user.userFirstName == null ? "Nulo" : user?.userName}
          </p>
          <p>{user?.userName}</p>
          <p>{user?.role}</p>
          <div className="list__button">
            <Button onClick={() => openModal(user)}>
              <AiFillEdit />
            </Button>
          </div>
        </article>
      ))}
      {isModalOpen && (
        <ModalUser
          user={selectedUser}
          onClose={closeModal}
          editMode={editMode}
          handleFormSubmit={handleFormSubmit}
          userForm={userForm}
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
