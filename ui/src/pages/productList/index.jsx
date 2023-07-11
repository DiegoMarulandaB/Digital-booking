import { Container } from "../../components/Container"
import { ListProduct } from "../../components/ListProduct"
import { SidebarLayout } from "../../layout/sidebar"

export const ProductList = () => {
  return (
    <>
      <Container>
        <SidebarLayout>
          <h1>Lista de productos</h1>
          <ListProduct />
        </SidebarLayout>
      </Container>
    </>
  )
}
