import React from "react";
import ReactDOM from "react-dom/client";
import { HashRouter } from "react-router-dom";
import { AppRoutes } from "./routes";
import "./theme/theme.css";
import { ContextProvider } from "./context";

import "react-date-range/dist/styles.css" // main css file
import "react-date-range/dist/theme/default.css" // theme css file

ReactDOM.createRoot(document.getElementById("root")).render(
	<React.StrictMode>
		<ContextProvider>
			<HashRouter>
				<AppRoutes />
			</HashRouter>
		</ContextProvider>
	</React.StrictMode>
);
