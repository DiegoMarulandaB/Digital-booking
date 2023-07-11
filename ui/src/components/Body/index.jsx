import { useGlobalState } from "../../context"
import { Product } from "../Product"
import { Recommendations } from "../Recommendations"
import { Search } from "../Search"
import './Body.css'

export const Body = () => {
  const { state } = useGlobalState()

  const hasFilterTours = state?.filterTours?.length > 0
  const hasToursCountryDate = state?.toursCountryDate?.length > 0

  return (
    <main className="body">
      <Search />
      <Product />
      {hasToursCountryDate ? (
        <Recommendations
          state={state}
          tours={state.toursCountryDate}
          title={"Busqueda de Tours"}
        />
      ) : hasFilterTours ? (
        <Recommendations
          state={state}
          tours={state.filterTours}
          title={"Tours filtrado por país"}
        />
      ) : (
        <Recommendations
          state={state}
          tours={state.tours}
          title={"Tours"}
        />
      )}
    </main>
  )
}
