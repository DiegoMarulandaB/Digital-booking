import { Container } from "../../components/Container"
import { CardDash } from "../../components/Dashboard/CardDash";
import { SidebarLayout } from "../../layout/sidebar";
import { useGlobalState } from "../../context"
import { MdTour } from 'react-icons/md';

import './admin.css'
import { useEffect } from "react";

import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
import { MapSection } from "../../components/Dashboard/MapSection";

ChartJS.register(ArcElement, Tooltip, Legend);

export const Admin = () => {
  const { state, fetchUsers } = useGlobalState();

  useEffect(() => {
    fetchUsers()
  }, [])

  const arrLabel = state?.categories?.map(item => item.categoryName);

  function countCategoryOccurrences(tours) {
    const counts = {};
    tours?.forEach(tour => {
      const categoryId = tour?.categoryId;
      counts[categoryId] = (counts[categoryId] || 0) + 1;
    });
    return Object.entries(counts).map(([categoryId, count]) => ({ categoryId, count }));
  }
  const categoryOccurrences = countCategoryOccurrences(state?.tours);

  const chartData = {
    labels: arrLabel,
    datasets: [
      {
        data: categoryOccurrences.map(item => item.count),
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
        ]
      }
    ]
  };
  const options = {
    plugins: {
      legend: {
        position: 'right',
        align: 'center',
        borderRadius: 5,
        labels: {
          pointStyle: 'circle',
          usePointStyle: true,
        }
      },
    },
    shadowOffsetX: 2,
    shadowOffsetY: 2,
    shadowBlur: 5,
    shadowColor: 'rgba(0, 0, 0, 0.3)',
    maintainAspectRatio: false,
  };

  return (
    <Container>
      <SidebarLayout>
        <section className="admin__container">
          <div className="welcome">
            <div className="welcome__content">
              <h1 className="">Bienvenido al panel de control</h1>
              <p className="">Hola Jone Doe, ¡bienvenida a tu impresionante panel de control!</p>
            </div>
          </div>
          <section className="admin__stats">
            <CardDash array={state?.categories} name="Categorias" icon={MdTour} />
            <CardDash array={state?.tours} name="Tours" icon={MdTour} />
            <CardDash array={state?.users?.content} name="Usuarios" icon={MdTour} />
          </section>

          <section className="stats__container">
            <div className="chart__content">
              <h3>Tours por categoria</h3>
              <article className="chart__container">
                <Pie className="chart" data={chartData} options={options} />
              </article>
            </div>
            <article>
              <h3> Mapa de los tours</h3>
              <MapSection countries={state?.countries} />
            </article>
          </section>
        </section>

      </SidebarLayout >
    </Container >
  )
}