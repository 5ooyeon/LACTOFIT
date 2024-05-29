<template>
    <div class="col-4 post-container" @click="viewPost">
      <!-- Placeholder image source or move the image to public directory and update path accordingly -->
      <img :src=post.boardImgUrl>
    </div>
  
    <div class="modal-dialog modal-lg" v-if="isModalVisible">
      <div class="modal-content">
        <div class="modal-body">
          <div class="post-photo">
            <!-- Dynamic src binding -->
            <img :src="post.board_imgUrl">
          </div>
          <div class="post-body">
            <div class="writer-profile">
              <img :src="authStore.user.userProfileImage">
              <p class="writer-nickname">
                {{ authStore.user.userNickname }}
              </p>
              <!-- Conditional rendering example -->
              <button v-if="!isFollowing">팔로우</button>
            </div>
            <div>
              <button class="put-like">좋아요</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue';
  import { useAuthStore } from '@/stores/auth';
  
  const authStore = useAuthStore();

  const props = defineProps({
    post: {
      type: Object,
      required: true
    }
  })

  // console.log(post)

  const isModalVisible = ref(false);
  const isFollowing = ref(false); // Example state
  
  function viewPost() {
    isModalVisible.value = true;
  }
  
  </script>
  
  <style scoped>
  .post-container {
    border: 1px solid black;
    cursor: pointer; /* Indicates this is clickable */
  }
  
  .modal-body {
    display: flex;
  }
  
  .post-photo {
    width: 60%;
  }
  </style>
  