<!-- src/components/Cart.vue -->
<template>
  <div class="cart-page">
    <h1>My Cart</h1>
    <div v-if="store.cartSpecificGames.length === 0">Your cart is empty.</div>
    <div v-else class="games-list">
      <div
        v-for="game in store.cartSpecificGames"
        :key="game.serialNumber"
        class="game-item"
      >
        <h3>{{ game.title }}</h3>
        <p>{{ game.description }}</p>
        <p>Price: ${{ game.price }}</p>
        <button @click="removeFromCart(game)">Remove</button>
      </div>
    </div>
    <div class="checkout" v-if="store.cartSpecificGames.length > 0">
      <p>Total Price: ${{ totalPrice }}</p>
      <button @click="checkout">Checkout</button>
    </div>
  </div>
</template>

<script>
import { store } from "../store.js";
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8081",
});

export default {
  name: "Cart",
  setup() {
    return { store };
  },
  data() {
    return {
      totalPrice: 0,
    };
  },
  created() {
    if (store.user) {
      this.calculateTotalPrice();
    } else {
      this.$router.push("/login");
    }
  },
  methods: {
    calculateTotalPrice() {
      this.totalPrice = store.cartSpecificGames.reduce(
        (sum, game) => sum + game.price,
        0
      );
    },
    removeFromCart(game) {
      const index = store.cartSpecificGames.findIndex(
        (g) => g.serialNumber === game.serialNumber
      );
      if (index !== -1) {
        store.cartSpecificGames.splice(index, 1);
        localStorage.setItem(
          "cartSpecificGames",
          JSON.stringify(store.cartSpecificGames)
        );
        this.calculateTotalPrice();
      }
    },
    async checkout() {
      try {
        // Create a new order
        const orderRequest = {
          orderDate: new Date().toISOString().split("T")[0],
          cardNumber: store.user.cardNumber || 123456789,
          customerId: store.user.id,
        };
        const orderResponse = await axiosClient.post("/orders", orderRequest);
        const orderNumber = orderResponse.data.orderNumber;

        // Add each SpecificGame to the order
        for (const game of store.cartSpecificGames) {
          await axiosClient.put(
            `/specificGames/${game.serialNumber}/addToOrder`,
            {
              orderNumber: orderNumber,
            }
          );
        }

        // Clear the cart
        store.cartSpecificGames = [];
        localStorage.removeItem("cartSpecificGames");
        this.totalPrice = 0;
        alert("Checkout successful!");
        this.$router.push("/my-games");
      } catch (e) {
        console.error(e);
        alert("Checkout failed.");
      }
    },
  },
};
</script>

<style scoped>
.cart-page {
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
.checkout {
  margin-top: 20px;
}
</style>
