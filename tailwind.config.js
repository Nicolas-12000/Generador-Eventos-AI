/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}", // Asegura que Tailwind escanee todos los archivos JS/JSX/TS/TSX en src/
  ],
  theme: {
    extend: {
      colors: {
        primary: "#3B82F6", // Azul de Tailwind (puedes personalizarlo)
        secondary: "#10B981", // Verde esmeralda
        danger: "#EF4444",// Rojo
        'event-primary': '#4F46E5',
        'event-secondary': '#10B981', 
      },
      fontFamily: {
        sans: ["Inter", "sans-serif"], // Si usas la fuente Inter (opcional)
      },
    },
  },
  plugins: [
    require("@tailwindcss/forms"), 
  ],
};