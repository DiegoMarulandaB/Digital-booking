/* eslint-disable no-control-regex */
// import Swal from "sweetalert2";

import { useForm } from "../../hooks/useForm";

import { Container } from "../../components/Container";
import { Input } from "../../components/Input";
import { Button } from "../../components/Button";

import "./Login.css";

const initialState = {
	userName: "",
	password: "",
};

const validationsForm = async (form) => {
	const errors = {};

	if (!form.userName.trim()) {
		errors.userName = "Correo es requerido";
	}

	let regex = new RegExp(
		"([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])"
	);

	if (!regex.test(form.userName)) {
		errors.userName = "Correo no válido";
	}

	if (!form.password.trim()) {
		errors.password = "Contraseña es requerida";
	}
	return errors;
};

const urlParam = "login";
const redirectTo = "/user";

export const Login = () => {
	const { form, errors, handleChange, handleLogin } = useForm(
		initialState,
		validationsForm,
		urlParam,
		redirectTo
	);

	return (
		<Container>
			<h1 className="form_title">Iniciar Sesión</h1>
			<form
				className="login__form"
				onSubmit={handleLogin}>
				<Input
					displayLabel="Correo electrónico"
					label="userName"
					type="text"
					onChange={handleChange}
					value={form.userName}
					errorMessage={errors.userName}
				/>
				<Input
					displayLabel="Contraseña"
					label="password"
					type="password"
					onChange={handleChange}
					value={form.password}
					errorMessage={errors.password}
				/>
				<Button type="primary">Ingresar</Button>
			</form>
		</Container>
	);
};
