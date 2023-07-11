import './Feature.css'
import { MdSportsHandball, MdOutlineEmojiFoodBeverage, MdPark } from 'react-icons/md';
import { GiMountainClimbing, GiJungle } from 'react-icons/gi';
import { FaCampground, FaNapster } from 'react-icons/fa';
import { GiBastet } from 'react-icons/gi';

export const icons = [
  { value: 1, icon: <GiJungle />, name: "selva" },
  { value: 2, icon: <GiBastet />, name: "Paseos" },
  { value: 3, icon: <MdSportsHandball />, name: "Actividades deportivas" },
  { value: 4, icon: <MdOutlineEmojiFoodBeverage />, name: "Comida Tradicional" },
  { value: 5, icon: <MdPark />, name: "Parques naturales" },
  { value: 6, icon: <GiMountainClimbing />, name: "Vida silvestre" },
  { value: 7, icon: <FaCampground />, name: "Campamentos" },
  { value: 8, icon: <FaNapster />, name: "Caminatas guiadas" },
];

export const Feature = ({ feature }) => {

  function renderIcon(feature) {
    const icon = icons.find(icon => icon.value === feature);
    return icon ? icon.icon : '';

  }
  return (
    <section className='feature'>
      <h2 className='feature__title'>Experiencia que se ofrecen</h2>
      <ul className='feature__list'>
        {feature?.length > 0 ? (
          feature?.map((item, i) => (
            <li key={i} className='feature__item'>
              <span className='item-icon'>{renderIcon(item.id)}</span>
              <span className='item-description'>{item.featureName}</span>
            </li>
          ))
        ) : (
          icons?.map((item, i) => (
            <li key={i} className='feature__item'>
              <span className='item-icon'>{item.icon}</span>
              <span className='item-description'>{item.name}</span>
            </li>
          ))
        )}
      </ul>
    </section>
  )
}
