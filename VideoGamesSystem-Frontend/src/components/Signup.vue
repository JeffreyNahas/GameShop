<!-- src/components/Signup.vue -->
<template>
  <div class="signup-container">
    <h1>Create an Account</h1>
    <form @submit.prevent="signup">
      <div>
        <label for="userName">Username:</label>
        <input type="text" v-model="userName" required />
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" required />
      </div>
      <div>
        <label for="phoneNumber">Phone Number:</label>
        <input type="number" v-model.number="phoneNumber" required />
      </div>
      <div>
        <label for="address">Address:</label>
        <input type="text" v-model="address" required />
      </div>
      <button type="submit">Create Account</button>
    </form>
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    <p>
      Already have an account?
      <a @click="goToLogin">Log in here</a>
    </p>
  </div>
</template>

<script>
import axios from "axios";
import { store } from "../store.js";

const axiosClient = axios.create({
  baseURL: "http://localhost:8081",
});

export default {
  name: "Signup",
  data() {
    return {
      userName: "",
      email: "",
      password: "",
      phoneNumber: null,
      address: "",
      errorMessage: "",
    };
  },
  methods: {
    async signup() {
      const newUser = {
        userName: this.userName,
        email: this.email,
        password: this.password,
        phoneNumber: this.phoneNumber,
        address: this.address,
      };
      try {
        const response = await axiosClient.post("/customers", newUser);
        // Optionally log the user in immediately, idk if I liked this feature we can talk more later
        localStorage.setItem("user", JSON.stringify(response.data));
        localStorage.setItem("userType", "customer");
        store.user = response.data;
        store.userType = "customer";
        this.$router.push("/home");
      } catch (e) {
        console.error(e);
        if (
          e.response &&
          e.response.data &&
          e.response.data.errors &&
          e.response.data.errors[0]
        ) {
          this.errorMessage = e.response.data.errors[0];
        } else {
          this.errorMessage = "An error occurred during account creation";
        }
      }
    },
    goToLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.signup-container {
  max-width: 400px;
  margin: 0 auto;
}
.error-message {
  color: red;
}
</style>
