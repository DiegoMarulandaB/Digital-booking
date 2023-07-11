import './Input.css'

/**
 * Componente de entrada reutilizable que incluye una etiqueta, un campo de entrada, un manejo de error y una imagen opcional.
 *
 * @param {string} props.imageSrc - La fuente de la imagen (opcional).
 * @param {string} props.label - La etiqueta del campo de entrada (opcional).
 * @param {string} props.errorMessage - El mensaje de error a mostrar (opcional).
 */

export const Input = ({ iconSrc, displayLabel, label, errorMessage, ...props }) => {
  const hasError = errorMessage !== undefined && errorMessage !== '';
  const hasImageSrc = iconSrc ? "" : "input--noImage"

  return (
    <label htmlFor={label} className={`input__label ${hasError ? 'error' : ''}`}>
      {displayLabel}
      <div className='input'>
        <input {...props} id={label} name={label} className={`input__field ${hasImageSrc} ${hasError && "error"}`} />
        <img src={iconSrc} alt="" className="input__image" onClick={() => document.getElementById(label).focus()} />
      </div>
      {hasError && <p className="input--error">{errorMessage}</p>}
    </label>
  )
}

export const Select = ({ options, displayLabel, label, errorMessage, ...props }) => {
  const hasError = errorMessage !== undefined && errorMessage !== '';

  return (
    <label htmlFor={label} className={`input__label ${hasError ? 'error' : ''}`}>
      {displayLabel}
      <div className='input'>
        <select {...props} id={label} name={label} className={`input__field  ${hasError && "error"}`} >
          {options.map((option, idx) => (
            <option key={idx} value={option.value}>{option.label}</option>
          ))}
        </select>
      </div>
      {hasError && <p className="input--error">{errorMessage}</p>}
    </label>
  )
}