import axios from "axios";

export async function fetchExercises() {
  const response = await axios.get("http://localhost:8080/exercises/");
  return response.data;
}
