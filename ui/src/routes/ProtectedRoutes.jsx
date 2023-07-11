import { Navigate, Outlet } from "react-router-dom";
import { useGlobalState } from "../context";

const ProtectedRoutes = () => {
	const { isAuthenticated } = useGlobalState();

	if (!isAuthenticated)
		<Navigate
			to="/"
			replace
		/>;

	return <Outlet />;
};

export default ProtectedRoutes;
