import { Container } from "../../components/Container";
import { ListCountry } from "../../components/ListCountry";
import { SidebarLayout } from "../../layout/sidebar";

export const CountryList = () => {
	return (
		<>
			<Container>
				<SidebarLayout>
					<h1>Lista de Paises</h1>
					<ListCountry />
				</SidebarLayout>
			</Container>
		</>
	);
};
