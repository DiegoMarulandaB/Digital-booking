import './CardDash.css'

export const CardDash = ({ array, name, icon: Icon }) => {
  return (
    <article className="admin__card">
      <Icon size={35} className="admin__card-icon" />
      <div className="admin__card-stats">
        <h3 className="">{array?.length}</h3>
        <p className="">{name}</p>
      </div>
    </article>
  )
}
