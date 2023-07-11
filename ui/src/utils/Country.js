import axios from "axios"

const URL_COUNTRY = "https://countriesnow.space/api/v0.1/countries/capital"

export const getCountries = async () => {
  return await axios.get(URL_COUNTRY)
}

export const getCountryCapital = async (countryName) => {
  return await axios.post(URL_COUNTRY, {
    country: countryName,
  })
}

export const getCountryPositions = async (countryName) => {
  return await axios.post(
    "https://countriesnow.space/api/v0.1/countries/positions",
    {
      country: countryName,
    }
  )
}
