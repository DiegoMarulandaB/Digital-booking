import { DetailedCard } from "../Card";
import { Pagination } from "../Pagination";
import { usePagination } from "../../hooks/usePagination";
import "./Recommendations.css";

export const Recommendations = ({ state, tours, title }) => {
	const { selectedCategory, categories } = state;
	const { currentPage, goToNextPage, goToPrevPage } = usePagination(7);

	const filteredTours = tours?.filter((tour) => tour?.categoryId === selectedCategory);
	const shuffledTours = tours?.sort(() => Math.random() - 0.5);

	const getCategoryName = (categoryId) => {
		const category = categories?.find((cat) => cat?.id === categoryId);
		return category ? category.categoryName : "";
	};

	const filteredToursItemsPerPage = 4; // Elemento por pagina
	const shuffledToursItemsPerPage = 10; // Elemento por pagina

	//Score:
	const score = {
		Malo: 3,
		Aceptable: 5,
		Satisfactorio: 6,
		Bueno: 8,
		MuyBueno: 9,
		Excelente: 10
	};

	function getScore(classification) {
		if (Object.prototype.hasOwnProperty.call(score, classification)) {
			return score[classification];
		} else {
			return "Invalid classification";
		}
	}

	const renderTours = (toursToRender, itemsPerPage) => {
		return toursToRender
			.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage)
			.map((tour) => (
				<DetailedCard
					key={tour.id}
					id={tour.id}
					title={tour.tourName}
					description={tour.tourDescription}
					imageSrc={tour?.images[0]?.imageUrl}
					classification={tour.tourClassification}
					category={getCategoryName(tour.categoryId)}
					score={getScore(tour.tourClassification)}
				/>

			));
	};

	const filteredToursToShow = renderTours(filteredTours || [], filteredToursItemsPerPage);
	const shuffledToursToShow = renderTours(shuffledTours || [], shuffledToursItemsPerPage);

	const filteredToursTotalPages = Math.ceil((filteredTours?.length || 0) / filteredToursItemsPerPage);
	const shuffledToursTotalPages = Math.ceil((shuffledTours?.length || 0) / shuffledToursItemsPerPage);

	const totalPages = filteredToursToShow.length > 0 ? filteredToursTotalPages : shuffledToursTotalPages;

	return (
		<section>
			<h2>{title}</h2>
			<div className="recommendations__content">
				{filteredToursToShow.length > 0 ? filteredToursToShow : shuffledToursToShow}
			</div>
			<div className="recommendations__buttons">
				<Pagination
					currentPage={currentPage}
					totalPages={totalPages}
					onNextPage={goToNextPage}
					onPrevPage={goToPrevPage}
				/>
			</div>
		</section>
	);
};
