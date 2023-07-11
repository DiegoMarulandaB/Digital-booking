import { Route, Routes } from "react-router-dom"

import { Home } from "../pages/home"
import { NotFound } from "../pages/notFound"
import Detail from "../pages/Detail"
import { ProductList } from "../pages/productList"
import { Admin } from "../pages/admin"
import { Register } from "../pages/register"
import { Success } from "../pages/success"
import { CategoryList } from "../pages/categoryList"
import Header from "../components/Header/header"
import Footer from "../components/Footer/footer"
import { Login } from "../pages/login"
import { SignUp } from "../pages/signUp"
import { User } from "../pages/user"
import { BookingSuccessful } from "../pages/bookingSuccessful"
import { BookingFailure } from "../pages/bookingFailure"
import { HistoryReservations } from "../pages/historyReservations"

import ProtectedRoutes from "./ProtectedRoutes"
import { CountryList } from "../pages/countryList"
import { UserList } from "../pages/userList"
import { useGlobalState } from "../context"
import { Favorites } from "../pages/favorites"
import { BookingPage } from "../pages/booking"

export const AppRoutes = () => {
  const { user } = useGlobalState()
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/detalle/:id/*" element={<Detail />} />

        <Route element={<ProtectedRoutes />}>
          <Route path="/user" element={<User />} />
          {user?.role && <Route path="/admin" element={<Admin />} />}
          <Route path="/favorites" element={<Favorites />} />
          <Route path="/product-list" element={<ProductList />} />
          <Route path="/category-list" element={<CategoryList />} />
          <Route path="/country-list" element={<CountryList />} />
          <Route path="/user-list" element={<UserList />} />

          <Route path="/booking/:id" element={<BookingPage />} />
          <Route path="/booking-success" element={<BookingSuccessful />} />
          <Route path="/booking-failure" element={<BookingFailure />} />
          <Route path="/reservations" element={<HistoryReservations />} />
        </Route>

        <Route path="/register" element={<Register />} />
        <Route path="/register-success" element={<Success />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />

        <Route path="*" element={<NotFound />} />
      </Routes>
      <Footer />
    </>
  )
}
