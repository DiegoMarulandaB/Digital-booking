import './SidebarHeader.css'

export const SidebarHeader = () => {

  return (
    <div className="sidebar__header">
      <img
        className="sidebar__img"
        src="https://uniim1.shutterfly.com/ng/services/mediarender/THISLIFE/021036514417/media/23148907008/medium/1501685726/enhance"
        alt="" />
      <div className="sidebar__content">
        <h5 className="">
          <a className="" href="#">Jone Doe</a>
        </h5>
        <p className="">Administrador digital de experiencia</p>
      </div>
    </div>
  )
}
