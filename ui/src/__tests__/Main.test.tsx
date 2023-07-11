import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Header from "../components/Header/header";

describe("Header", () => {
  beforeEach(() => {
    render(<Header />);
  });

  test("renders logo", () => {
    const logoElement = screen.getByAltText("Logo");
    expect(logoElement).toBeInTheDocument();
  });

  test("renders 'Iniciar Sesión' button", () => {
    const loginButton = screen.getByText("Iniciar Sesión");
    expect(loginButton).toBeInTheDocument();
  });

  test("clicking 'Iniciar Sesión' button navigates to '/login'", () => {
    const loginButton = screen.getByText("Iniciar Sesión");
    fireEvent.click(loginButton);
    expect(window.location.pathname).toBe("/login");
  });

});
