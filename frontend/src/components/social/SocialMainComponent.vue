<template>
  <div class="container">
    <div class="menu">
      <span class="select-menu" :class="{ active: selectedMenu === 'all' }" @click="selectMenu('all')">전체</span>
      <span class="select-menu" :class="{ active: selectedMenu === 'following' }" @click="selectMenu('following')">팔로잉</span>
    </div>
    <div class="content-area">
      <ul v-if="store.returnBoardList.length != 0" class="board-list">
        <li v-for="board in store.returnBoardList" :key="board.id" class="board-item">
          <img class="board-thumbnail" :src="board.board.boardImgUrl" @click="showBoardDetail(board)">
        </li>
      </ul>
      <p v-if="store.returnBoardList.length == 0" class="no-post-message">게시물이 없습니다.</p>
    </div>
    <button type="button" class="btn btn-primary" @click="showUploadModal">작성하기</button>
    <!-- Upload Modal -->
<div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="uploadModalLabel">Upload Image</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="form-check form-switch">
          <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" v-model="writeBoard.boardVisibility">
          <label class="form-check-label" for="flexSwitchCheckChecked">공개</label>
        </div>
        <div class="routine-list-container">
          <div class="routine-list">
            <div v-for="routine in myRoutines" :key="routine.routineId" class="form-check">
              <input class="form-check-input" type="radio" name="routine" :id="`routine${routine.routineId}`" :value="routine.routineId" v-model="writeBoard.routineId">
              <label class="form-check-label" :for="`routine${routine.routineId}`">
                {{ routine.routineName }}
                <ul>
                  <li v-for="exercise in routine.exercises" :key="exercise.exerciseId">
                    {{ exercise.exerciseName }}
                  </li>
                </ul>
              </label>
            </div>
          </div>
        </div>
        <div class="mb-3">
          <div class="file-upload-wrapper">
            <input type="file" id="formFile" @change="onFileChange" class="file-input">
            <label for="formFile" class="file-upload-label">
              <i class="fas fa-cloud-upload-alt"></i> 업로드
            </label>
          </div>
        </div>
        <div v-if="imageUrl">
          <img :src="imageUrl" class="img-fluid" alt="Image preview">
        </div>
        <div class="write-form">
          <textarea v-model="writeBoard.boardContent" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px"></textarea>
        </div>
        <button class="btn btn-primary mt-3" @click="registPost">등록</button>
      </div>
    </div>
  </div>
</div>

<!-- Board Detail Modal -->
<div class="modal fade" id="boardDetailModal" tabindex="-1" aria-labelledby="boardDetailModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="detail-content">
          <div class="detail-image">
            <img :src="selectedBoard?.board.boardImgUrl" class="img-fluid" alt="Board Image">
          </div>
          <div class="detail-info">
            <div @click="goProfile(selectedBoard?.writer.userId)" class="writer-info">
              <img :src="selectedBoard?.writer.userProfileImage" alt="" class="writer-profile-image">
              <p class="board-detail-writer-name">{{selectedBoard?.writer.userNickname}}</p>
            </div>

            <div v-if="authStore.user.userId !== selectedBoard?.writer.userId" class="follow-container" @click="followUnfollow(selectedBoard?.writer.userId)">
              <svg class="feather feather-heart" stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg">
                <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
              </svg>
              <div class="follow-action">
                <span class="option-1" v-if="!isFollower">팔로우</span>
                <span class="option-2" v-else>언팔로우</span>
              </div>
            </div>


            <div class="routine-section">
              <p class="board-detail-routine-label">{{ selectedBoard?.writer.userNickname }} 님의 운동 루틴</p>
              <div class="board-detail-routine" v-for="routine in selectedBoard?.RoutineComponents" :key="routine.exercise_name">
                <p class="detail-routine-name"><em class="detail-routine-part">[{{ routine.exercise_part }}]</em> {{routine.exercise_name}}</p>
              </div>
            </div>
            <p class="board-content">{{ selectedBoard?.board.boardContent }}</p>
            <p class="board-detail-regdate">{{ selectedBoard?.board.boardRegDate }}</p>
            <div class="board-like">
              <div title="Like" class="heart-container">
                <input id="Give-It-An-Id" class="checkbox" type="checkbox" :checked="hasUserLiked" @click="boardLike(selectedBoard?.board.boardId)">
                <div class="svg-container">
                  <svg xmlns="http://www.w3.org/2000/svg" class="svg-outline" viewBox="0 0 24 24">
                    <path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Zm-3.585,18.4a2.973,2.973,0,0,1-3.83,0C4.947,16.006,2,11.87,2,8.967a4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,11,8.967a1,1,0,0,0,2,0,4.8,4.8,0,0,1,4.5-5.05A4.8,4.8,0,0,1,22,8.967C22,11.87,19.053,16.006,13.915,20.313Z"></path>
                  </svg>
                  <svg xmlns="http://www.w3.org/2000/svg" class="svg-filled" viewBox="0 0 24 24">
                    <path d="M17.5,1.917a6.4,6.4,0,0,0-5.5,3.3,6.4,6.4,0,0,0-5.5-3.3A6.8,6.8,0,0,0,0,8.967c0,4.547,4.786,9.513,8.8,12.88a4.974,4.974,0,0,0,6.4,0C19.214,18.48,24,13.514,24,8.967A6.8,6.8,0,0,0,17.5,1.917Z"></path>
                  </svg>
                  <svg xmlns="http://www.w3.org/2000/svg" height="100" width="100" class="svg-celebrate">
                    <polygon points="10,10 20,20"></polygon>
                    <polygon points="10,50 20,50"></polygon>
                    <polygon points="20,80 30,70"></polygon>
                    <polygon points="90,10 80,20"></polygon>
                    <polygon points="90,50 80,50"></polygon>
                    <polygon points="80,80 70,70"></polygon>
                  </svg>
                </div>
                <p class="board-like-count">{{ boardLikes.length }}</p>
              </div>
            </div>
            <button class="btn btn-danger" v-if="authStore.user.userId == selectedBoard?.writer.userId" @click="deleteBoard(selectedBoard?.board.boardId)">글 삭제하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue';
import { Modal } from 'bootstrap';
import axios from 'axios';
import { useSocialStore } from '@/stores/social';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const store = useSocialStore();
const authStore = useAuthStore();
const router = useRouter();
const selectedMenu = ref('all');

onMounted(() => {
  store.getAllBoardList();
  myFollowingList();
});

const file = ref(null);
const imageUrl = ref(null);
let uploadModal = null;
let boardDetailModal = null;
const selectedBoard = ref(null);
const myRoutines = ref([]);
const boardLikes = ref([]);
const userId = authStore.user.userId;
const writeBoard = ref({
  userId: userId,
  routineId: 0,
  boardContent: '',
  boardVisibility: false
});
const myFollowing = ref([]);
const isFollower = ref(false);

const onFileChange = (e) => {
  const selectedFile = e.target.files[0];
  if (selectedFile && selectedFile.type.startsWith('image/')) {
    file.value = selectedFile;
    imageUrl.value = URL.createObjectURL(selectedFile);
  } else {
    file.value = null;
    imageUrl.value = null;
  }
};

const registPost = () => {
  const formData = new FormData();
  formData.append('file', file.value);
  formData.append('userId', writeBoard.value.userId);
  formData.append('routineId', writeBoard.value.routineId);
  formData.append('boardContent', writeBoard.value.boardContent);
  formData.append('boardVisibility', writeBoard.value.boardVisibility);

  axios.post('http://localhost:8080/boards/', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
  .then((response) => {
    file.value = null;
    imageUrl.value = null;
    writeBoard.value.routineId = 0;
    writeBoard.value.boardContent = '';
    writeBoard.value.boardVisibility = false;
    hideUploadModal();
    store.getAllBoardList();
  })
  .catch((err) => {
    console.error('Error registering post:', err);
  });
};

const showUploadModal = () => {
  axios.get(`http://localhost:8080/routines/users/${userId}`)
    .then((response) => {
      myRoutines.value = response.data;
      if (!uploadModal) {
        const modalElement = document.getElementById('uploadModal');
        uploadModal = new Modal(modalElement);
      }
      uploadModal.show();
    })
    .catch((err) => {
      console.error('Error fetching routines:', err);
    });
};

const hideUploadModal = () => {
  if (uploadModal) {
    uploadModal.hide();
  }
};

const showBoardDetail = async (board) => {
  selectedBoard.value = board;
  try {
    const response = await axios.get(`http://localhost:8080/boardlikes/${board.board.boardId}`);
    boardLikes.value = response.data;
  } catch (error) {
    console.error('Error fetching board likes:', error);
  }

  isFollower.value = myFollowing.value.some(following => following.followingUserId === selectedBoard.value.writer.userId);

  if (!boardDetailModal) {
    const modalElement = document.getElementById('boardDetailModal');
    boardDetailModal = new Modal(modalElement);
  }
  boardDetailModal.show();
};

const getAllPost = () => {
  store.getAllBoardList();
  selectedMenu.value = 'all';
};

const getFollowingPost = () => {
  store.getFollowingBoardList();
  selectedMenu.value = 'following';
};

const boardLike = (boardId) => {
  const userId = authStore.user.userId;

  axios.post('http://localhost:8080/boardlikes/', { boardId, userId })
    .then((response) => {
      const liked = response.data.liked;
      if (liked) {
        boardLikes.value.push({ user_id: userId });
      } else {
        boardLikes.value = boardLikes.value.filter(like => like.user_id !== userId);
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

const hasUserLiked = computed(() => {
  return boardLikes.value.some(like => like.user_id === authStore.user.userId);
});

const myFollowingList = () => {
  axios.get(`http://localhost:8080/follows/following/${authStore.user.userId}`)
    .then((response) => {
      myFollowing.value = response.data;
    });
};

const followUnfollow = (writerId) => {
  const userId = authStore.user.userId;
  const followingUserId = writerId;

  axios.post('http://localhost:8080/follows/', { followingUserId, userId })
    .then((response) => {
      isFollower.value = !isFollower.value;
      if (isFollower.value) {
        myFollowing.value.push({ followingUserId });
      } else {
        myFollowing.value = myFollowing.value.filter(following => following.followingUserId !== followingUserId);
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

const goProfile = (userId) => {
  boardDetailModal.hide();
  router.push({ name: 'MyPageView', params: { id: userId } });
};

const deleteBoard = (boardId) => {
  axios.delete('http://localhost:8080/boards/' + boardId)
    .then((response) => {
      store.getAllBoardList();
      boardDetailModal.hide();
    })
    .catch((err) => {
      console.error('Error deleting board:', err);
    });
};

const selectMenu = (menu) => {
  if (menu === 'all') {
    getAllPost();
  } else if (menu === 'following') {
    getFollowingPost();
  }
  selectedMenu.value = menu;
};

</script>
<style scoped>
.container {
  margin-top: 20px;
  padding: 20px;
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.menu {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.select-menu {
  margin: 0 10px;
  cursor: pointer;
  font-weight: bold;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
}

.select-menu:hover,
.select-menu.active {
  background-color: #007bff;
  color: #fff;
}

.content-area {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.board-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 0;
  list-style: none;
}

.board-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.board-item:hover {
  transform: translateY(-5px);
}

.board-thumbnail {
  width: 100%;
  cursor: pointer;
  border-radius: 10px;
  transition: transform 0.3s;
}

.board-thumbnail:hover {
  transform: scale(1.05);
}

.no-post-message {
  font-size: 1.2em;
  color: #888;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  margin-top: 20px;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}

.modal-content {
  border-radius: 10px;
}

.modal-header {
  border-bottom: none;
}

.modal-title {
  font-weight: bold;
  color: #333;
}

.btn-close {
  background-color: transparent;
  border: none;
}

.form-check-label {
  margin-bottom: 0;
}

.form-check-input:checked + .form-check-label {
  font-weight: bold;
}

.file-upload-wrapper {
  position: relative;
  display: inline-block;
  width: 100%;
  text-align: center;
}

.file-input {
  width: 0.1px;
  height: 0.1px;
  opacity: 0;
  overflow: hidden;
  position: absolute;
  z-index: -1;
}

.file-upload-label {
  display: inline-block;
  cursor: pointer;
  font-size: 1.25rem;
  color: #007bff;
  background-color: #fff;
  border: 2px solid #007bff;
  padding: 10px 20px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.file-upload-label:hover {
  background-color: #007bff;
  color: #fff;
}

.file-upload-label i {
  margin-right: 10px;
}

.detail-content {
  display: flex;
  gap: 20px;
}

.detail-image {
  flex: 2;
}

.detail-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.writer-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  margin-bottom: 20px;
}

.writer-profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.board-detail-writer-name {
  font-weight: bold;
  font-size: 1.1em;
}

.follow-container {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 15px;
  background-color: #007bff;
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.follow-container:hover {
  background-color: #0056b3;
}

.heart-container {
  position: relative;
  width: 50px;
  height: 50px;
  transition: 0.3s;
  display: flex;
}

.heart-container .checkbox {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0;
  z-index: 20;
  cursor: pointer;
}

.heart-container .svg-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.heart-container .svg-outline,
.heart-container .svg-filled {
  fill: #007bff;
  position: absolute;
}

.heart-container .svg-filled {
  animation: keyframes-svg-filled 1s;
  display: none;
}

.heart-container .svg-celebrate {
  position: absolute;
  animation: keyframes-svg-celebrate 0.5s;
  animation-fill-mode: forwards;
  display: none;
  stroke: #007bff;
  fill: #007bff;
  stroke-width: 2px;
}

.heart-container .checkbox:checked ~ .svg-container .svg-filled {
  display: block;
}

.heart-container .checkbox:checked ~ .svg-container .svg-celebrate {
  display: block;
}

@keyframes keyframes-svg-filled {
  0% {
    transform: scale(0);
  }

  25% {
    transform: scale(1.2);
  }

  50% {
    transform: scale(1);
    filter: brightness(1.5);
  }
}

@keyframes keyframes-svg-celebrate {
  0% {
    transform: scale(0);
  }

  50% {
    opacity: 1;
    filter: brightness(1.5);
  }

  100% {
    transform: scale(1.4);
    opacity: 0;
    display: none;
  }
}

.board-like-count {
  margin: auto;
  font-size: 1.2em;
  font-weight: bold;
  color: #333;
}

.routine-section {
  margin-top: 20px;
}

.board-detail-routine-label {
  font-weight: bold;
  font-size: 1.2em;
  color: #333;
}

.board-detail-routine {
  margin-bottom: 10px;
}

.detail-routine-name {
  font-size: 1em;
  color: #555;
}

.detail-routine-part {
  font-weight: bold;
}

.board-content {
  margin-top: 10px;
  font-size: 1em;
  color: #333;
}

.board-detail-regdate {
  margin-top: 10px;
  font-size: 0.9em;
  color: #888;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #c82333;
  border-color: #bd2130;
}
</style>