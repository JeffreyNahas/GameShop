<!-- src/components/MyGames.vue -->
<template>
  <div class="my-games-page">
    <h1>My Games</h1>
    <div v-if="games.length === 0">You haven't purchased any games yet.</div>
    <div v-else class="games-list">
      <div v-for="game in games" :key="game.serialNumber" class="game-item">
        <h3>{{ game.title }}</h3>
        <p>{{ game.description }}</p>
        <p>Price: ${{ game.price }}</p>
        <p>Serial Number: {{ game.serialNumber }}</p>
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
  name: "MyGames",
  data() {
    return {
      games: [],
    };
  },
  created() {
    if (store.user) {
      this.fetchMyGames();
    } else {
      this.$router.push("/login");
    }
  },
  methods: {
    async fetchMyGames() {
      try {
        // Fetch orders for the customer
        const response = await axiosClient.get(
          `/orders/customer/${store.user.id}`
        );
        const orders = response.data; // List of SpecificOrderResponseDto

        this.games = [];
        for (const order of orders) {
          // Fetch specific games in the order
          const specificGamesResponse = await axiosClient.get(
            `/orders/${order.orderNumber}/specificGames`
          );
          const specificGames = specificGamesResponse.data; // List of SpecificGameResponseDto

          // Add to the games array, avoiding duplicates
          for (const game of specificGames) {
            if (!this.games.find((g) => g.serialNumber === game.serialNumber)) {
              this.games.push(game);
            }
          }
        }
      } catch (e) {
        console.error(e);
      }
    },
  },
};
</script>

<style scoped>
.my-games-page {
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
</style>
