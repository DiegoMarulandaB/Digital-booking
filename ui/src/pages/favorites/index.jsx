import { DetailedCard } from "../../components/Card"
import { Container } from "../../components/Container"
import { useGlobalState } from "../../context"

export const Favorites = () => {
  const { state } = useGlobalState()

  return (
    <Container>
      <h1>Mis favoritos</h1>
      <div className="recommendations__content">
        {state?.favorites?.map((tour) => (
          <DetailedCard
            key={tour.id}
            id={tour.id}
            title={tour.title}
            description={tour.description}
            imageSrc={tour.imageSrc}
            classification={tour.classification}
            category={tour.category}
            score={tour.score}
          />
        ))}
      </div>
    </Container>
  )
}
