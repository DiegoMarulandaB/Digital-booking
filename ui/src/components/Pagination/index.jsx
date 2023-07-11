import { Button } from '../Button'
import './Pagination.css'

export const Pagination = ({ currentPage, totalPages, onNextPage, onPrevPage }) => {

  if (totalPages <= 1) {
    return null;
  }

  const renderPageNumbers = () => {
    const pageNumbers = [];

    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.push(i);
    }

    return (
      <>
        {pageNumbers.map((pageNumber) => (
          <span
            key={pageNumber}
            className={`page-number ${pageNumber === currentPage ? 'active' : ''}`}
          >
            {pageNumber}
          </span>
        ))}
      </>
    );
  }

  return (
    <div className='page__content'>
      <Button onClick={onPrevPage} disabled={currentPage === 1}>
        Anterior
      </Button>
      {renderPageNumbers()}
      <Button onClick={onNextPage} disabled={currentPage === totalPages}>
        Siguiente
      </Button>
    </div>
  )
}