<!-- src/components/Wishlist.vue -->
<template>
  <div class="wishlist-page">
    <h1>My Wishlist</h1>
    <div v-if="games.length === 0">Your wishlist is empty.</div>
    <div v-else class="games-list">
      <div v-for="game in games" :key="game.id" class="game-item">
        <h2>{{ game.title }}</h2>
        <p>{{ game.description }}</p>
        <p>Price: ${{ game.price }}</p>
        <button @click="removeFromWishlist(game)">Remove</button>
        <button @click="addToCart(game)">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { store } from "../store.js";

const axiosClient = axios.create({
  baseURL: "http://localhost:8081",
});

export default {
  name: "Wishlist",
  setup() {
    return { store };
  },
  data() {
    return {
      games: [],
      wishlistId: null,
    };
  },
  created() {
    if (store.user) {
      this.fetchWishlist();
    } else {
      this.$router.push("/login");
    }
  },
  methods: {
    async fetchWishlist() {
      try {
        // Fetch the user's wishlist
        const wishlistResponse = await axiosClient.get(
          `/wishlists/customer/${store.user.id}`
        );
        this.wishlistId = wishlistResponse.data.id;

        // Fetch games in the wishlist
        const gamesResponse = await axiosClient.get(
          `/wishlists/${this.wishlistId}/games`
        );
        this.games = gamesResponse.data; // List of GameResponseDto
      } catch (e) {
        console.error(e);
      }
    },
    async removeFromWishlist(game) {
      try {
        await axiosClient.delete(
          `/wishlists/${this.wishlistId}/games/${game.id}`
        );
        this.games = this.games.filter((g) => g.id !== game.id);
      } catch (e) {
        console.error(e);
      }
    },
    async addToCart(game) {
      try {
        // Fetch available specific games for the selected game
        const response = await axiosClient.get(
          `/games/${game.id}/specificGames`
        );
        const specificGames = response.data; // List of SpecificGameResponseDto

        // Find an available specific game
        const availableSpecificGame = specificGames.find(
          (sg) => sg.availability
        );

        if (!availableSpecificGame) {
          alert("No available copies of this game.");
          return;
        }

        // Add the specific game to the cart
        const exists = store.cartSpecificGames.find(
          (sg) => sg.serialNumber === availableSpecificGame.serialNumber
        );

        if (!exists) {
          store.cartSpecificGames.push(availableSpecificGame);
          localStorage.setItem(
            "cartSpecificGames",
            JSON.stringify(store.cartSpecificGames)
          );
          alert("Game added to cart!");
        } else {
          alert("This game is already in your cart.");
        }
      } catch (e) {
        console.error(e);
        alert("Failed to add game to cart.");
      }
    },
  },
};
</script>

<style scoped>
/* Add styles here */
</style>
