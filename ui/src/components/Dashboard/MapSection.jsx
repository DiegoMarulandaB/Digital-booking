import { useEffect } from 'react';
import L from 'leaflet';

import 'leaflet/dist/leaflet.css';
import './Map.css';

export const MapSection = ({ countries }) => {

  useEffect(() => {
    const mapContainer = L.DomUtil.get('map');
    if (mapContainer != null) {
      mapContainer._leaflet_id = null;
    }

    const map = L.map('map').setView([0, 0], 1);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
    }).addTo(map);

    const customIcon = L.icon({
      iconUrl: 'https://www.iconpacks.net/icons/2/free-camping-location-icon-2958-thumb.png',
      iconSize: [32, 32],
      iconAnchor: [16, 32],
    });

    countries.forEach((country) => {
      const { latitude, longitude } = country;
      if (latitude !== null && longitude !== null) {
        L.marker([latitude, longitude], { icon: customIcon }).addTo(map);
      }
    });

    return () => {
      map.remove();
    };
  }, [countries]);

  return <div id="map" className="map-container"></div>;
};
