import { useState } from "react"
import { Button } from "../Button"
import { Link, useNavigate } from "react-router-dom"
import "./header.css"
import BurgerButton from "../BurgerButton/BurgerButton"
import Swal from "sweetalert2"
import { useGlobalState } from "../../context"

const Header = () => {
  const [clicked, setClicked] = useState(false)
  const { user, setIsAuthenticated, setUser } = useGlobalState()
  const navigate = useNavigate()
  const handleClick = () => {
    setClicked(!clicked)
  }

  const handleLogOut = () => {
    Swal.fire({
      title: `Quieres cerrar sesión?`,
      icon: "warning",
      showCancelButton: true,
      showConfirmButton: true,
    }).then((result) => {
      if (result.isConfirmed) {
        setIsAuthenticated(false)
        setUser(null)
        localStorage.clear()
        navigate("/")
      }
    })
  }

  return (
    <header>
      <div className="header_logo">
        <Link to="/" className="link">
          <img src="https://c3-e10-digitalbooking-imagenes.s3.us-east-2.amazonaws.com/header-svg.svg" alt="Logo" />
        </Link>
        <span>Vive la aventura</span>
      </div>

      {user ? (
        <nav>
          <Button type="primary" onClick={() => navigate("/user")}>
            {user?.username}
          </Button>
          <Button type="secondary" onClick={handleLogOut}>
            Cerrar sesión
          </Button>
        </nav>
      ) : (
        <nav>
          <Button type="primary" onClick={() => navigate("/login")}>
            Iniciar Sesión
          </Button>
          <Button type="secondary" onClick={() => navigate("/signup")}>
            Crear Cuenta
          </Button>
        </nav>
      )}

      <div className="burger">
        <BurgerButton clicked={clicked} handleClick={handleClick} />
      </div>
      {user ? (
        <div id="bgDiv" className={`initial ${clicked ? " active" : ""}`}>
          <div className="bgDiv__content">
            <p onClick={() => navigate("/user")}>{user?.sub}</p>
            <p onClick={handleLogOut}>Cerrar sesión</p>
          </div>
        </div>
      ) : (
        <div id="bgDiv" className={`initial ${clicked ? " active" : ""}`}>
          <div className="bgDiv__content">
            <p onClick={() => navigate("/login")}>Crear cuenta</p>
            <p onClick={() => navigate("/signup")}>Iniciar sesión</p>
          </div>
        </div>
      )}
    </header>
  )
}

export default Header
