import { Button } from "../../components/Button";
import { Container } from "../../components/Container";
import { Input, Select } from "../../components/Input";
import { useForm } from "../../hooks/useForm";
import { SidebarLayout } from "../../layout/sidebar";
import "./Register.css";

const initialState = {
	id: Date.now(),
	name: "",
	description: "",
	image_url: "",
	score: 0,
	capacity: 0,
	availability: true,
	price: 0,
	id_category: 0,
};

const validationsForm = async (form) => {
	const errors = {};

	if (!form.name.trim()) {
		errors.name = "Nombre es requerido";
	}
	if (!form.description.trim()) {
		errors.description = "Descripción es requerido";
	}
	let regex = new RegExp("/^https://images.unsplash.com/i");
	if (regex.test(form.image_url)) {
		errors.image_url = "No es un link de unplash válido";
	}
	if (!form.image_url.trim()) {
		errors.image_url = "Url Imagen es requerido";
	}
	if (!form.image_url.trim()) {
		errors.image_url = "Url Imagen es requerido";
	}
	if (form.capacity <= 0) {
		errors.capacity = "Al menos capacidad de (1) persona";
	}
	if (form.price <= 0) {
		errors.price = "Precio válido";
	}
	if (Number.isInteger(form.id_category)) {
		errors.id_category = "Debes seleccionar una categoria";
	}
	return errors;
};

const options = [
	{ label: "Ciudades Emblemáticas", value: 1 },
	{ label: "Tropical Paradise Expedition", value: 2 },
	{ label: "Magia Invernal", value: 3 },
	{ label: "Caminos perdidos", value: 4 },
];

const urlParam = "tours";
const redirectTo = "/product-list";

export const Register = () => {
	const { form, errors, handleChange, handleBlur, handleSubmit } = useForm(
		initialState,
		validationsForm,
		urlParam,
		redirectTo
	);

	return (
		<Container>
			<SidebarLayout>
				<h1 className="form_title">Agregar Paquete</h1>
				<form
					className="admin__form"
					onSubmit={handleSubmit}>
					<Input
						displayLabel="Nombre"
						label="name"
						type="text"
						onBlur={handleBlur}
						onChange={handleChange}
						value={form.name}
						errorMessage={errors.name}
					/>
					<Input
						displayLabel="Descripción"
						label="description"
						type="text"
						onBlur={handleBlur}
						onChange={handleChange}
						value={form.description}
						errorMessage={errors.description}
					/>
					<Input
						displayLabel="Imagen"
						label="image_url"
						type="text"
						onBlur={handleBlur}
						onChange={handleChange}
						value={form.image_url}
						errorMessage={errors.image_url}
					/>
					<Input
						displayLabel="Capacidad"
						label="capacity"
						type="number"
						onBlur={handleBlur}
						onChange={handleChange}
						value={form.capacity}
						errorMessage={errors.capacity}
					/>
					<Input
						displayLabel="Precio por Persona"
						label="price"
						type="number"
						onBlur={handleBlur}
						onChange={handleChange}
						value={form.price}
						errorMessage={errors.price}
					/>

					<Button type="primary">Agregar</Button>
				</form>
			</SidebarLayout>
		</Container>
	);
};
