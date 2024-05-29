// src/stores/social.js
import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';

const REST_BOARD_API = `http://localhost:8080/boards/`

export const useSocialStore = defineStore('social', () => {
    const returnBoardList = ref([])
    const getAllBoardList = () => {
      axios.get(REST_BOARD_API)
        .then((response) => {
          returnBoardList.value = response.data
        console.log(returnBoardList)
      })
    }

    const getFollowingBoardList = () => {
      const user = JSON.parse(sessionStorage.getItem('user'))
      axios.get(REST_BOARD_API+`following/`+user.userId)
      .then((response) => {
        returnBoardList.value = response.data
      })
      .catch((error) => {
        returnBoardList.value = []
      })
    }

    const getBoardLikeCnt = (boardId) => {
      axios.get(`http://localhost:8080/board-likes/${boardId}`)
      .then((response) => {
        
      })
    }

    return{
        returnBoardList,
        getFollowingBoardList,
        getAllBoardList
    }
  
});
