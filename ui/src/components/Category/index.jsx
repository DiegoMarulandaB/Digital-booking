import { useGlobalState } from "../../context";
import { SimpleCard } from "../Card";
// import { dataCategory } from "../Data/dataCategory";

import "./Category.css";

export const Category = () => {
	const { state } = useGlobalState();
	return (
		<section className="category">
			<h1>Categorias</h1>
			<div className="category__content">
				{state?.categories?.map((category) => (
					<SimpleCard
						key={category.id_categoria}
						imageSrc={category.imagen_url}
						title={category.nombre}
						description={category.descripcion}
					/>
				))}
			</div>
		</section>
	);
};
