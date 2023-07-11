import "../Button/Button.css"

/**
 * Componente de botón reutilizable.
 *
 * @param {string} props.type - El tipo de botón que se va a mostrar. Puede ser "primary" o "secondary". Requerido.
 * @param {ReactNode} props.children - El contenido del botón. Requerido.
 */

export const Button = ({ type, children, ...props }) => {
  const classNames = `btn${type === 'primary' ? ' btn__primary' : ' btn__secondary'}`;
  return (
    <button className={classNames} {...props}>
      {children}
    </button>
  )
}
