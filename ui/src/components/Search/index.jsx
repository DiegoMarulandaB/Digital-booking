/* eslint-disable react/display-name */
import { Input } from "../../components/Input"
import { Button } from "../Button"
import Select from 'react-select';
import Calendar from "../../assets/calendar.svg"
import "./Search.css"
import { useEffect, useRef, useState } from "react"
import { addDays, format } from "date-fns"
import { useGlobalState } from "../../context"
import { DateRange } from "react-date-range"
import { useUserLocationAndSorting } from "./userLocationAndSorting";
import axios from "axios";

export const Search = () => {
  const { state, user, fetchToursByCountry, fetchTourCountryDate } = useGlobalState()
  const [open, setOpen] = useState(false)
  const refOne = useRef(null)
  const [range, setRange] = useState([
    {
      startDate: addDays(new Date(), 4),
      endDate: addDays(new Date(), 2),
      key: "selection",
    },
  ])

  const handleHide = (e) => {
    if (refOne.current && !refOne.current.contains(e.target)) {
      setOpen(false)
    }
  }
  useEffect(() => {
    document.addEventListener("click", handleHide)
  }, [])

  const [isClearable, setIsClearable] = useState(true);
  const [selectedOption, setSelectedOption] = useState(null);

  //Coordenada del navegador y Ordenar por aproximidad
  const sortedCountries = useUserLocationAndSorting(state?.countries);

  const [options, setOptions] = useState([]);

  useEffect(() => {
    if (user?.latitude && user?.longitude) {
      const config = {
        latitude: user.latitude,
        longitude: user.longitude,
      };
      const url = "http://3.133.208.24:8000/countries/byDistance";
      axios.post(url, config).then((response) => {
        const newOptions = response.data.map(({ countryName, id }) => ({
          value: id,
          label: countryName,
        }));
        setSelectedOption(null);
        setIsClearable(false);
        setOptions(newOptions);
      });
    } else {
      const options = sortedCountries?.map(({ countryName, id }) => ({
        value: id,
        label: countryName,
      }));
      setSelectedOption(null);
      setIsClearable(false);
      setOptions(options);
    }
  }, [user, sortedCountries]);
  ///

  const handleSelectChange = (selectedOption) => {
    if (selectedOption === null) {
      setSelectedOption(null);
      setIsClearable(false);
    } else {
      setSelectedOption(selectedOption);
      setIsClearable(true);
    }
  };
  useEffect(() => {
    if (selectedOption === null) {
      fetchToursByCountry(0);
      setIsClearable(false);
    } else {
      const countryId = selectedOption.value;
      fetchToursByCountry(countryId);
      setIsClearable(true);
    }
  }, [selectedOption]);

  // Busqueda pais y fecha
  const country_id = selectedOption?.value
  const startDate = format(range[0].startDate, "yyyy-MM-dd");
  const endDate = format(range[0].endDate, "yyyy-MM-dd");

  const searchTours = async (e) => {
    e.preventDefault();
    if (country_id && startDate && endDate) {
      fetchTourCountryDate(country_id, startDate, endDate);
    }
  };
  const customStyles = {
    control: (provided) => ({
      ...provided,
      fontFamily: 'Popins, roboto',
      border: "none"
    }),
  };

  return (
    <section className="search">
      <h1 className="search__title">Busca ofertas de experiencia turística</h1>
      <form action="" className="search-form">
        <Select
          placeholder="Búsqueda por país"
          classNamePrefix="select"
          className="search__select"
          isClearable={isClearable}
          options={options}
          value={selectedOption}
          onChange={handleSelectChange}
          styles={customStyles}
        />
        <Input
          iconSrc={Calendar}
          placeholder="Check in - Check out"
          className="input-calendar"
          value={`${format(range[0].startDate, "dd/MM/yyyy")} a ${format(
            range[0].endDate,
            "dd/MM/yyyy"
          )}`}
          readOnly={true}
          onClick={() => setOpen((open) => !open)}
        />

        <div ref={refOne} className="calendar__search">
          {open && (
            <DateRange
              ref={refOne}
              editableDateInputs={true}
              moveRangeOnFirstSelection={false}
              ranges={range}
              months={2}
              direction="horizontal"
              onChange={(item) => setRange([item.selection])}
            />
          )}
        </div>

        <Button type="primary" onClick={searchTours}>Buscar</Button>
      </form>
    </section>
  )
}
