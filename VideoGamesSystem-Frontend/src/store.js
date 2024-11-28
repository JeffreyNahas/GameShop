import { reactive } from "vue";

export const store = reactive({
  user: JSON.parse(localStorage.getItem("user")) || null,
  userType: localStorage.getItem("userType") || null,
  cartSpecificGames:
    JSON.parse(localStorage.getItem("cartSpecificGames")) || [],
  // Other shared state can be added here
});
