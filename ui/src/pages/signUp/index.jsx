/* eslint-disable no-control-regex */
import { Container } from "../../components/Container"
import { Input } from "../../components/Input"
import { Button } from "../../components/Button"
import { useForm } from "../../hooks/useForm"
import { useState } from "react"

const validationsForm = async (form) => {
  const errors = {}
  if (!form.userName.trim()) {
    errors.userName = "Nombre es requerido"
  }
  if (form.userName.length < 3) {
    errors.userName = "Nombre formato Inválido"
  }
  if (!form.userLastName.trim()) {
    errors.userLastName = "Apellido es requerido"
  }
  if (form.userLastName.length < 3) {
    errors.userLastName = "Apellido formato Inválido"
  }
  let regex = new RegExp(
    "([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])"
  )
  if (!form.userEmail.trim()) {
    errors.userEmail = "Email es requerido"
  }
  if (!form.confirmPassword.trim()) {
    errors.confirmPassword = "Confirmacion es requerido"
  }
  if (!form.password.trim()) {
    errors.password = "Contraseña es requerido"
  }
  if (!regex.test(form.userEmail)) {
    errors.userEmail = "Email Inválido"
  }
  if (form.password != form.confirmPassword) {
    errors.confirmPassword = "Contraseñas no coinciden"
  }

  return errors
}

const urlParam = "sign-up"
const redirectTo = "/user"

export const SignUp = () => {
  const [lat, setLat] = useState(0)
  const [long, setLong] = useState(0)

  navigator.geolocation.getCurrentPosition((position) => {
    const latitude = position.coords.latitude
    setLat(latitude)

    const longitude = position.coords.longitude
    setLong(longitude)
  })

  const initialState = {
    userName: "",
    userLastName: "",
    userFirstName: "",
    userEmail: "",
    latitude: lat,
    longitude: long,
    password: "",
    confirmPassword: "",
  }

  const { form, errors, handleChange, handleSubmit } = useForm(
    initialState,
    validationsForm,
    urlParam,
    redirectTo
  )

  return (
    <Container>
      <h1 className="form_title">Crear Cuenta</h1>
      <form className="admin__form" onSubmit={handleSubmit}>
        <Input
          displayLabel="Nombre"
          label="userName"
          type="text"
          onChange={handleChange}
          value={form.userName}
          errorMessage={errors.userName}
        />
        <Input
          displayLabel="Apellido"
          label="userLastName"
          type="text"
          onChange={handleChange}
          value={form.userLastName}
          errorMessage={errors.userLastName}
        />
        <Input
          displayLabel="Correo Electrónico"
          label="userEmail"
          type="text"
          onChange={handleChange}
          value={form.userEmail}
          errorMessage={errors.userEmail}
        />
        <Input
          displayLabel="Contraseña"
          label="password"
          type="password"
          onChange={handleChange}
          value={form.password}
          errorMessage={errors.password}
        />
        <Input
          displayLabel="Confirmar Contraseña"
          label="confirmPassword"
          type="password"
          onChange={handleChange}
          value={form.confirmPassword}
          errorMessage={errors.confirmPassword}
        />
        <br />
        <Button type="primary">Registrarse</Button>
      </form>
    </Container>
  )
}
