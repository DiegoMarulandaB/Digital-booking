import Swal from "sweetalert2";

export const Toast = (message, type) => {
	const Toast = Swal.mixin({
		toast: true,
		position: "top-right",
		showConfirmButton: false,
		timer: "2500",
		timerProgressBar: true,
	});

	Toast.fire({
		icon: type,
		title: message,
	});
};
