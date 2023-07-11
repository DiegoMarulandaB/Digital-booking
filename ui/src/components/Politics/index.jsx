import "./Politics.css";

export const Politics = () => {
  return (
    <section className="politics">
      <h2 className="politics__title">Que Tenés que saber</h2>
      <div className="politics__container">
        <article className="politics__item">
          <h4>Política de privacidad</h4>
          <p>
            Estas políticas describe cómo tratamos la información personal que
            recopilamos cuando visita nuestro sitio web y nuestros servicios... <a href="https://c3-e10-digitalbooking-imagenes.s3.us-east-2.amazonaws.com/Poli%CC%81tica+de+privacidad-equipo10.pdf" target="_blank" rel="noreferrer">ver más</a>
          </p>
        </article>
        <article className="politics__item">
          <h4>Aviso Legal</h4>
          <p>
            Consideraciones legales que debe tener en cuenta al visitar nuestro
            sitio web y posteriormente aceptación de los servicios que
            ofrecemos... <a href="https://c3-e10-digitalbooking-imagenes.s3.us-east-2.amazonaws.com/aviso+legal-equipo10.pdf" target="_blank" rel="noreferrer">ver más</a>
          </p>
        </article>
        <article className="politics__item">
          <h4>Términos y condiciones</h4>
          <p>
            Por favor lea términos y condiciones de servicio para este sitio
            web, antes de utilizarlo, ya que al acceder usted manifiesta su
            aceptación de los T&C... <a href="https://c3-e10-digitalbooking-imagenes.s3.us-east-2.amazonaws.com/Te%CC%81rminos+y+Condiciones+-equipo10.pdf" target="_blank" rel="noreferrer">ver más</a>
          </p>
        </article>
      </div>
    </section>
  );
};
