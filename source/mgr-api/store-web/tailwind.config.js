/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#c64600", // main orange
          hover: "#a63a00"
        },
        secondary: {
            DEFAULT: "#a1f1e1", // pale turquoise/teal
            hover: "#8ae0d0"
        },
        dark: "#1e1e1e",
        gray: {
          light: "#f5f5f5",
          DEFAULT: "#e5e5e5",
          dark: "#a3a3a3"
        }
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
