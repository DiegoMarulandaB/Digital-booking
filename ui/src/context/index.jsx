/* eslint-disable no-extra-boolean-cast */
import {
  createContext,
  useContext,
  useReducer,
  useState,
  useEffect,
} from "react"
import { AppReducer, actions } from "./reducer"
import axios from "axios"
import jwt_decode from "jwt-decode"
import { Toast } from "../utils/Toast"
import Swal from "sweetalert2"

export const BASE_URL =
  // import.meta.env.VITE_API_URL ||
  "http://3.133.208.24:8000"

const initialState = {
  selectedCategory: null,
  favorites: JSON.parse(localStorage.getItem("favorites")) || [],
}

export const ContextGlobal = createContext()

export const ContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(AppReducer, initialState)
  const [getFavorites, setGetFavorites] = useState([])
  const [user, setUser] = useState(null)
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const token = null || JSON.parse(localStorage.getItem("token"))

  const fetchCategories = async () => {
    await axios.get(`${BASE_URL}/category`).then((response) => {
      dispatch({
        type: actions.GET_CATEGORIES,
        payload: response.data,
      })
    })
  }
  const updateCategory = async (categoryId, updatedData) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.put(
        `${BASE_URL}/category/${categoryId}`,
        updatedData,
        config
      )
      dispatch({
        type: actions.UPDATE_CATEGORY,
        payload: response.data,
      })
    } catch (error) {
      console.error("Error updating category:", error)
    }
  }
  const addCategory = async (newCategoryData) => {
    try {
      const formData = new FormData()
      formData.append("file", newCategoryData.categoryImageFile[0])
      formData.append("Category", JSON.stringify(newCategoryData))
      const config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.post(
        `${BASE_URL}/category`,
        formData,
        config
      )
      dispatch({
        type: actions.ADD_CATEGORY,
        payload: response.data,
      })
    } catch (error) {
      console.error("Error adding category:", error)
    }
  }
  const deleteCategory = async (categoryId) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }

      await axios.delete(`${BASE_URL}/category/${categoryId}`, config)
      dispatch({
        type: actions.REMOVE_CATEGORY,
        payload: categoryId,
      })
    } catch (error) {
      console.error("Error deleting category:", error)
    }
  }

  const fetchTours = async () => {
    await axios.get(`${BASE_URL}/tours`).then((response) => {
      dispatch({
        type: actions.GET_TOURS,
        payload: response.data,
      })
    })
  }

  const fetchTourCountryDate = async (country_id, startDate, endDate) => {
    try {
      const response = await axios.get(`${BASE_URL}/tours/filterByCountryAndDates/${country_id}/${startDate}/${endDate}`);
      const data = response.data;

      if (data.length === 0) {
        Swal.fire({
          title: 'No se encontraron tours para las fechas seleccionadas.',
          icon: 'info',
          confirmButtonColor: '#6D9886',
        });
      } else {

        const searchTour = {
          isSearch: true,
          startDate: startDate,
          endDate: endDate,
          country_id: country_id
        };
        localStorage.setItem('searchTour', JSON.stringify(searchTour));

        dispatch({
          type: actions.GET_TOURSCOUNTRYDATE,
          payload: data,
        });
      }
    } catch (error) {
      console.error("Error search tour:", error)
    }
  }

  const updateTour = async (tourId, updatedData) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.put(
        `${BASE_URL}/tours/${tourId}`,
        updatedData,
        config
      )
      dispatch({
        type: actions.UPDATE_TOUR,
        payload: response.data,
      })
    } catch (error) {
      console.error("Error updating tour:", error)
    }
  }

  const addTour = async (newTourData) => {
    const formData = new FormData()
    for (let i = 0; i < newTourData.toursImageFile.length; i++) {
      formData.append("files", newTourData.toursImageFile[i])
    }
    formData.append("Tour", JSON.stringify(newTourData))
    try {
      const config = {
        "Content-Type": "multipart/form-data",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.post(`${BASE_URL}/tours`, formData, config)
      if (response)
        dispatch({
          type: actions.CREATE_TOUR,
          payload: response.data,
        })
    } catch (error) {
      console.error("Error adding tour:", error)
    }
  }

  const deleteTour = async (tourId) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      await axios.delete(`${BASE_URL}/tours/${tourId}`, config)
      dispatch({
        type: actions.REMOVE_TOUR,
        payload: tourId,
      })
    } catch (error) {
      console.error("Error deleting tour:", error)
    }
  }

  const fetchTourById = async (tourId) => {
    return await axios.get(`${BASE_URL}/tours/${tourId}`)
  }

  const fetchUsers = async () => {
    return await axios
      .get(`${BASE_URL}/users`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        dispatch({
          type: actions.GET_USERS,
          payload: response.data,
        })
      })
  }
  const fetchUserByEmail = async (email) => {
    return await axios.get(`${BASE_URL}/users/name/${email}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  }

  const makeAdminRole = async (userName) => {
    return await axios.post(
      `${BASE_URL}/users/admin`,
      { userName },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
  }
  const makeUserRole = async (userName) => {
    return await axios.post(
      `${BASE_URL}/users/user`,
      { userName },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
  }
  const fetchCountry = async () => {
    await axios.get(`${BASE_URL}/countries`).then((response) => {
      dispatch({
        type: actions.GET_COUNTRIES,
        payload: response.data,
      })
    })
  }

  const createCountry = async (newCountryData) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.post(
        `${BASE_URL}/countries`,
        newCountryData,
        config
      )
      if (response) {
        Toast("Pais agregado", "success")
        dispatch({
          type: actions.ADD_COUNTRY,
          payload: response.data,
        })
      }
    } catch (error) {
      Toast("Error", "error")

      console.error("Error adding country:", error)
    }
  }
  const updateCountry = async (countryId, updatedData) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.put(
        `${BASE_URL}/countries/${countryId}`,
        updatedData,
        config
      )
      dispatch({
        type: actions.UPDATE_COUNTRY,
        payload: response.data,
      })
    } catch (error) {
      console.error("Error updating country:", error)
    }
  }
  const deleteCountry = async (countryId) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.delete(
        `${BASE_URL}/countries/${countryId}`,
        config
      )
      if (response) {
        Toast("Pais eliminado", "success")
        dispatch({
          type: actions.REMOVE_COUNTRY,
          payload: countryId,
        })
      }
    } catch (error) {
      Toast("Error", "error")
      console.error("Error deleting country:", error)
    }
  }
  const fetchToursByCountry = async (countryId) => {
    await axios
      .get(`${BASE_URL}/tours/country/${countryId}`)
      .then((response) => {
        if (countryId > 0 && response.data.length === 0) {
          Swal.fire({
            icon: 'info',
            title: 'El país no cuenta con tours.',
            confirmButtonColor: '#6D9886',
          });
        } else {
          dispatch({
            type: actions.SEARCH_BY_COUNTRY,
            payload: response.data,
          })
        }
      })
  }
  const fetchFeature = async () => {
    await axios.get(`${BASE_URL}/features`).then((response) => {
      dispatch({
        type: actions.GET_FEATURES,
        payload: response.data,
      })
    })
  }
  const fetchByTours = async (tourId) => {
    await axios.get(`${BASE_URL}/tours/${tourId}`).then((response) => {
      console.log("searchTour", response.data)
      dispatch({
        type: actions.FETCH_BY_TOURS,
        payload: response.data,
      })
    })
  }

  useEffect(() => {
    fetchCategories()
    fetchTours()
    fetchTourCountryDate()
    fetchCountry()
    fetchFeature()
    fetchByTours()
  }, [])

  useEffect(() => {
    const decodeResponse = async () => {
      setIsAuthenticated(true)
      const decoded = await jwt_decode(token)
      const userInfo = await fetchUserByEmail(decoded.sub)
      setUser(userInfo.data)
    }

    decodeResponse()
  }, [token])

  useEffect(() => {
    localStorage.setItem("favorites", JSON.stringify(state.favorites))
  }, [state.favorites])

  const isTourInFavorites = (newTour) =>
    state?.favorites?.find((tour) => tour.id === newTour.id)

  const addFav = (tour) => {
    if (isTourInFavorites(tour)) {
      Toast("Tour removido", "success")

      dispatch({
        type: actions.REMOVE_FROM_FAVORITE,
        payload: tour,
      })
    } else {
      Toast("Tour agregado", "success")

      dispatch({
        type: actions.ADD_TO_FAVORITE,
        payload: tour,
      })
    }
  }

  const createReservation = async (newReservation) => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
      const response = await axios.post(
        `${BASE_URL}/reservations`,
        newReservation,
        config
      )
      if (response) {
        Toast("Reserva exitosa", "success")
      }
    } catch (error) {
      Toast("Error", "error")
      console.error("Error:", error)
    }
  }

  const fetchReservations = async (userId) => {
    return await axios.get(`${BASE_URL}/reservations/byUser/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  }

  const value = {
    state,
    dispatch,
    // CATEGORY
    setSelectedCategory: (category) =>
      dispatch({
        type: actions.SET_SELECTED_CATEGORY,
        payload: category,
      }),
    updateCategory,
    addCategory,
    deleteCategory,
    // TOURS
    updateTour,
    addTour,
    deleteTour,
    fetchByTours,
    fetchTourCountryDate,
    fetchTourById,
    // AUTHENTICATION
    user,
    setUser,
    isAuthenticated,
    setIsAuthenticated,
    token,
    // USERS
    fetchUsers,
    fetchUserByEmail,
    makeAdminRole,
    makeUserRole,
    // COUNTRY
    fetchCountry,
    createCountry,
    updateCountry,
    deleteCountry,
    //SEARCH
    fetchToursByCountry,
    // FAVORITES
    getFavorites,
    setGetFavorites,
    addFav,
    // FEATURE
    fetchFeature,
    // RESERVATION
    createReservation,
    fetchReservations,
  }
  return (
    <ContextGlobal.Provider value={value}>{children}</ContextGlobal.Provider>
  )
}

export const useGlobalState = () => useContext(ContextGlobal)
