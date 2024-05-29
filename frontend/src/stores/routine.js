import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useAuthStore } from './auth'
import axios from 'axios';

export const useRoutineStore = defineStore('routine', () => {

  const user = useAuthStore().user;

  const getMyRoutines = () => {
    const userId = user.userId
    axios.get(REST_BOARD_API+`/following/`+user.user_id)
    .then((response) => {
      returnBoardList.value = response.data
    })
  }

  const getAllExercises = () => {
    
  }

  return { user, getMyRoutines }
})
