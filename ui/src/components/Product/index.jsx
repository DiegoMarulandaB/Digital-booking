import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { SimpleCard } from "../Card";
import { useGlobalState } from "../../context";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./Product.css";

export const Product = () => {
  const { state, setSelectedCategory } = useGlobalState();
  const [slidesToShow, setSlidesToShow] = useState(4);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth < 414) {
        setSlidesToShow(1);
      } else if (window.innerWidth < 768) {
        setSlidesToShow(2);
      } else {
        setSlidesToShow(4);
      }
    };

    window.addEventListener("resize", handleResize);
    handleResize();

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  const handleCategoryClick = (id) => {
    setSelectedCategory(id);
  };

  const NextArrow = (props) => {
    const { className, style, onClick } = props;
    return (
      <div
        className={className}
        style={{
          ...style,
          display: "none",
        }}
        onClick={onClick}
      />
    );
  };

  const PrevArrow = (props) => {
    const { className, style, onClick } = props;
    return (
      <div
        className={className}
        style={{
          ...style,
          left: "-50px",
          zIndex: 1,
        }}
        onClick={onClick}
      />
    );
  };

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: slidesToShow,
    slidesToScroll: slidesToShow,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />,
  };

  return (
    <section className="product">
      <h2>Buscar por categorias</h2>
      <Slider {...settings}>
        {state?.categories?.map((data) => (
          <div key={data.id}>
            <Link
              className="product_link"
              to={"/"}
              onClick={() => handleCategoryClick(data.id)}
            >
              <SimpleCard
                imageSrc={data?.imageCategory?.imageUrl}
                title={data?.categoryName}
                description={data?.categoryDescription}
              />
            </Link>
          </div>
        ))}
      </Slider>
    </section>
  );
};

