/* eslint-disable no-unused-vars */
import { useState } from "react"
import axios from "axios"
import { BASE_URL } from "../context"
import { useNavigate } from "react-router-dom"
import Swal from "sweetalert2"

export const useForm = (initialState, validateForm, urlParam, redirectTo) => {
  const [form, setForm] = useState(initialState)
  const [errors, setErrors] = useState({})
  const navigate = useNavigate()
  const booking = JSON.parse(localStorage.getItem("booking"))

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm({
      ...form,
      [name]: value,
    })
  }

  const handleBlur = (e) => {
    handleChange(e)
    setErrors(validateForm(form))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const hasErrors = await validateForm(form)
    setErrors(hasErrors)
    console.log(hasErrors)

    if (
      Object.keys(hasErrors).length === 0 &&
      Object.keys(errors).length === 0
    ) {
      Swal.fire({
        title: "Cargando...",
        timer: 2000,
        timerProgressBar: true,
        showConfirmButton: false,
      })

      console.log({
        userEmail: form.userEmail,
        password: form.password,
        userName: form.userEmail,
        userFirstName: form.userName,
        userLastName: form.userLastName,
        latitude: Number(form.latitude),
        longitude: Number(form.longitude),
      })

      console.log(`${BASE_URL}/${urlParam}`)
      await axios
        .post(`${BASE_URL}/${urlParam}`, {
          // userEmail: form.userEmail,
          password: form.password,
          userName: form.userEmail,
          userFirstName: form.userName,
          userLastName: form.userLastName,
          latitude: parseFloat(form.latitude),
          longitude: parseFloat(form.longitude),
        })
        .then((res) => {
          localStorage.setItem("token", JSON.stringify(res.data.jwt))
          Swal.fire({
            icon: "success",
            timer: 2000,
            showConfirmButton: false,
          })
          setForm(initialState)
          navigate(redirectTo)
          window.location.reload()
        })
        .catch((err) => {
          Swal.fire({
            icon: "error",
            text: err,
            showConfirmButton: true,
          })
          console.log(err)
        })
    }
    return
  }

  const handleLogin = async (e) => {
    e.preventDefault()
    const hasErrors = await validateForm(form)
    setErrors(hasErrors)

    if (
      Object.keys(hasErrors).length === 0 &&
      Object.keys(errors).length === 0
    ) {
      await axios
        .post(`${BASE_URL}/${urlParam}`, form)
        .then((res) => {
          localStorage.setItem("token", JSON.stringify(res.data.jwt))
          Swal.fire({
            icon: "success",
            timer: 2000,
            showConfirmButton: false,
          })
          setForm(initialState)
          booking
            ? navigate(`/booking/${booking?.idTour}`)
            : navigate(redirectTo)

          window.location.reload()
        })
        .catch((err) => {
          Swal.fire({
            icon: "error",
            text: err,
            showConfirmButton: true,
          })
        })
    }
  }

  return {
    form,
    errors,
    handleChange,
    handleBlur,
    handleSubmit,
    handleLogin,
  }
}
