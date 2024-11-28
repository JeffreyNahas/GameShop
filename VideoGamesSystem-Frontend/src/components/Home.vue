<template>
  <div>
    <h1>Welcome, {{ store.user.userName }}!</h1>
    <div v-if="store.userType === 'customer'">
      <!-- Customer-specific content -->
      <p>You are logged in as a customer.</p>
    </div>
    <div v-else-if="store.userType === 'staff'">
      <!-- Staff-specific content -->
      <p>You are logged in as a staff member.</p>
      <!-- Add staff functionalities -->
    </div>

    <div class="home-page">
      <h2>All Games</h2>
      <div class="filters">
        <label for="sort">Sort by:</label>
        <select v-model="sortOption" @change="sortGames">
          <option value="price">Price</option>
          <option value="category">Category</option>
          <option value="consoleType">Console Type</option>
        </select>
      </div>
      <div class="games-list">
        <div v-for="game in games" :key="game.id" class="game-item">
          <h3>{{ game.title }}</h3>
          <p>{{ game.description }}</p>
          <p>Price: ${{ game.price }}</p>
          <p>Category: {{ game.category }}</p>
          <p>Console: {{ game.consoleType }}</p>
          <p>Available: {{ game.availableQuantity > 0 ? "Yes" : "No" }}</p>
          <button
            @click="addToCart(game)"
            :disabled="game.availableQuantity === 0"
          >
            Add to Cart
          </button>
          <button @click="addToWishlist(game)">Add to Wishlist</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { store } from "../store.js";
// import axios from 'axios'; // Commented out since I'm using local data

export default {
  name: "Home",
  setup() {
    return { store };
  },
  data() {
    return {
      sortOption: "price",
      games: [
        {
          id: 1,
          title: "Where's my Rami?",
          description:
            "An adventure where you try to find the work Rami did on this deliverable.",
          price: 60,
          category: "Action",
          consoleType: "PS4",
          availableQuantity: 1, // Number of available SpecificGame instances
          specificGames: [
            { serialNumber: 1001, availability: true },
            { serialNumber: 1002, availability: true },
          ],
        },
        {
          id: 2,
          title: "Mystery tests",
          description:
            "A game where rami finds all non functional tests and comments them out instead of fixing code",
          price: 40,
          category: "Puzzle",
          consoleType: "Switch",
          availableQuantity: 1,
          specificGames: [{ serialNumber: 2001, availability: true }],
        },
        {
          id: 3,
          title: "Tennis Pro",
          description: "Game where you get to hit Emile with a tennis racket",
          price: 50,
          category: "Racing",
          consoleType: "PS4",
          availableQuantity: 0,
          specificGames: [{ serialNumber: 3001, availability: false }],
        },
        {
          id: 4,
          title: "AYCE HotPot",
          description:
            "Extreme sport where everyone eats hotpot until they throw up",
          price: 55,
          category: "Sports",
          consoleType: "Switch",
          availableQuantity: 3,
          specificGames: [
            { serialNumber: 4001, availability: true },
            { serialNumber: 4002, availability: true },
            { serialNumber: 4003, availability: true },
          ],
        },
        {
          id: 5,
          title: "Fantazizi",
          description: "Explore the fantasyzi world.",
          price: 45,
          category: "RPG",
          consoleType: "PS4",
          availableQuantity: 1,
          specificGames: [{ serialNumber: 5001, availability: true }],
        },
      ],
    };
  },
  created() {
    if (!store.user || !store.userType) {
      this.$router.push("/login");
    } else {
      this.sortGames();
    }
  },
  methods: {
    sortGames() {
      if (this.sortOption === "price") {
        this.games.sort((a, b) => a.price - b.price);
      } else if (this.sortOption === "category") {
        this.games.sort((a, b) => a.category.localeCompare(b.category));
      } else if (this.sortOption === "consoleType") {
        this.games.sort((a, b) => a.consoleType.localeCompare(b.consoleType));
      }
    },
    addToCart(game) {
      if (game.availableQuantity === 0) {
        alert("No available copies of this game.");
        return;
      }

      // Find an available SpecificGame instance
      const availableSpecificGame = game.specificGames.find(
        (sg) => sg.availability
      );

      if (!availableSpecificGame) {
        alert("No available copies of this game.");
        return;
      }

      // Add the SpecificGame to the cart
      const exists = store.cartSpecificGames.find(
        (sg) => sg.serialNumber === availableSpecificGame.serialNumber
      );

      if (!exists) {
        // Mark the SpecificGame as unavailable
        availableSpecificGame.availability = false;
        game.availableQuantity -= 1;

        // Add game details to the SpecificGame object for display purposes
        const cartGame = {
          ...availableSpecificGame,
          title: game.title,
          description: game.description,
          price: game.price,
        };

        store.cartSpecificGames.push(cartGame);
        localStorage.setItem(
          "cartSpecificGames",
          JSON.stringify(store.cartSpecificGames)
        );

        alert("Game added to cart!");
      } else {
        alert("This game is already in your cart.");
      }
    },
    addToWishlist(game) {
      // Simulating as I am not currently connected to backend
      alert(`Game "${game.title}" added to wishlist!`);
    },
  },
};
</script>

<style scoped>
.home-page {
  padding: 20px;
}
.games-list {
  display: flex;
  flex-wrap: wrap;
}
.game-item {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  width: 200px;
}
.filters {
  margin-bottom: 20px;
}
</style>
