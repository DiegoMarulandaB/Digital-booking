import { Link } from "react-router-dom"
import { SidebarHeader } from "../../components/Sidebar/SidebarHeader"
import { GoHome } from 'react-icons/go';
import { BiUser, BiCategoryAlt, BiWorld } from 'react-icons/bi';
import { RiFileList3Line } from 'react-icons/ri';
import "./SidebarLayout.css"

export const SidebarLayout = ({ children }) => {
  return (
    <div className="admin-layout">
      <nav className="admin-menu">
        <SidebarHeader />
        <ul>
          <li>
            <GoHome size={20} />
            <Link to="/admin">Admin</Link>
          </li>
          <li>
            <BiUser size={20} />
            <Link to="/user-list">Usuarios</Link>
          </li>
          <li>
            <RiFileList3Line size={20} />
            <Link to="/product-list">Productos</Link>
          </li>
          <li>
            <BiCategoryAlt size={20} />
            <Link to="/category-list">Categorias</Link>
          </li>
          <li>
            <BiWorld size={20} />
            <Link to="/country-list">Paises</Link>
          </li>
        </ul>
      </nav>
      <main className="admin-content">{children}</main>
    </div>
  )
}
