import { useEffect } from "react"
import { useForm } from "react-hook-form"
import { Button } from "../Button"
import { GrFormClose } from "react-icons/gr"
import "./Modal.css"
import { useGlobalState } from "../../context"
import { Toast } from "../../utils/Toast"

export const ModalUser = ({
  onClose,
  editMode,
  user,
  handleFormSubmit,
  userForm,
}) => {
  const { register, handleSubmit, setValue } = useForm()
  const { makeAdminRole, makeUserRole, fetchUsers } = useGlobalState()

  useEffect(() => {
    if (editMode && userForm) {
      setValue("userName", userForm.userName)
      setValue("role", userForm.role)
    } else {
      setValue("userName", "")
      setValue("role", "")
    }
  }, [editMode, userForm, setValue])

  const handleRoleChange = async (email, role) => {
    if (role == "USER") {
      await makeAdminRole(email)
      await fetchUsers()
      Toast("Cambio a rol Admin", "success")
    } else if (role == "ADMIN") {
      await makeUserRole(email)
      await fetchUsers()
      Toast("Cambio a rol User", "success")
    }
  }

  return (
    <section className="modal__overlay">
      <div className="modal__content">
        <h3>Editar Usuario</h3>

        <form className="modal__form" onSubmit={handleSubmit(handleFormSubmit)}>
          <label htmlFor="userName">Email</label>
          <input
            type="text"
            id="userName"
            placeholder="Usuario"
            defaultValue={user?.userName || ""}
            disabled
            {...register("userName")}
          />
          <label htmlFor="role">Rol Actual</label>
          <input
            type="text"
            id="role"
            placeholder="Usuario"
            defaultValue={user?.role || ""}
            disabled
            {...register("role")}
          />
        </form>
        <br />
        <Button
          onClick={() => handleRoleChange(userForm.userName, userForm.role)}
        >
          Cambiar Rol Actual
        </Button>

        <span className="modal__close" onClick={onClose}>
          <GrFormClose />
        </span>
      </div>
    </section>
  )
}
