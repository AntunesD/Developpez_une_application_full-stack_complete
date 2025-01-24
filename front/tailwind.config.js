/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}", "./src/app/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#7763C5",
          light: "#A093E2",
          dark: "#58429A",
        },
        secondary: {
          DEFAULT: "#E9D5FF",
          light: "#F3E8FF",
          dark: "#D8B4FE",
        },
        card: {
          DEFAULT: "#f3f4f6",
        },
      },
    },
  },
  plugins: [],
};
