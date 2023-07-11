import { Container } from "../../components/Container";
import { ListUser } from "../../components/ListUser";
import { SidebarLayout } from "../../layout/sidebar";

export const UserList = () => {
	return (
		<>
			<Container>
				<SidebarLayout>
					<h1>Lista de usuarios</h1>
					<ListUser />
				</SidebarLayout>
			</Container>
		</>
	);
};
