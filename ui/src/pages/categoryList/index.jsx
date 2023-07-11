import { Container } from "../../components/Container";
import { ListCategory } from "../../components/ListCategory";
import { SidebarLayout } from "../../layout/sidebar";

export const CategoryList = () => {
	return (
		<>
			<Container>
				<SidebarLayout>
					<h1>Lista de categoria</h1>
					<ListCategory />
				</SidebarLayout>
			</Container>
		</>
	);
};
