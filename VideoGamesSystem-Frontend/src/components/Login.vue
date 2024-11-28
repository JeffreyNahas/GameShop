<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="login">
      <div>
        <label for="userName">Username:</label>
        <input type="text" v-model="userName" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" required />
      </div>
      <div>
        <label for="userType">User Type:</label>
        <select v-model="userType" required>
          <option value="customer">Customer</option>
          <option value="staff">Staff</option>
        </select>
      </div>
      <button type="submit">Login</button>
    </form>
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    <p>
      Don't have an account?
      <a @click="goToSignup">Create one here</a>
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
  name: "Login",
  data() {
    return {
      userName: "",
      password: "",
      userType: "customer",
      errorMessage: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axiosClient.post("/login", {
          userName: this.userName,
          password: this.password,
          userType: this.userType,
        });
        localStorage.setItem("user", JSON.stringify(response.data));
        localStorage.setItem("userType", this.userType);
        store.user = response.data;
        store.userType = this.userType;
        this.$router.push("/home");
      } catch (e) {
        console.error("Login failed:", e.response);
        if (e.response && e.response.status === 401) {
          this.errorMessage = "Invalid username or password";
        } else {
          this.errorMessage = "An error occurred during login";
        }
      }
    },
    goToSignup() {
      this.$router.push("/signup");
    },
  },
};
</script>

<style scoped>
.error-message {
  color: red;
}
</style>
