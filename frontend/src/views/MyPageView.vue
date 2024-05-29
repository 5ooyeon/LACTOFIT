<template>
  <div class="profile-page container">
    <div class="tabs" v-if="isCurrentUser">
      <span :class="['tab', { active: selectedTab === 'feed' }]" @click="selectTab('feed')">피드</span>
      <span :class="['tab', { active: selectedTab === 'analysis' }]" @click="selectTab('analysis')">상점</span>
      <span :class="['tab', { active: selectedTab === 'setting' }]" @click="selectTab('setting')">내정보수정</span>
    </div>
    <div class="main-content">
      <aside class="profile">
        <img :src="userInfo.user?.userProfileImage" alt="Profile Picture" class="profile-picture object-fit-cover" />
        <h2 class="nickname">{{ userInfo.user?.userNickname }}</h2>
        <span @click="showFollowingModal">팔로잉 {{ userInfo.following.length }}</span><span @click="showFollowersModal">팔로워 {{ userInfo.follower.length }} </span>
        

        <div v-if="authStore.user.userId !== userInfo.user?.userId" class="follow-container" @click="followUnfollow">
          <svg class="feather feather-heart" stroke-linejoin="round" stroke-linecap="round" stroke-width="2" stroke="currentColor" fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg">
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
          </svg>
          <div class="follow-action">
            <span class="option-1" v-if="!isFollower">팔로우</span>
            <span class="option-2" v-else>언팔로우</span>
          </div>
        </div>
      </aside>
      <div class="content-area">
        <div class="content">
          <div v-if="selectedTab === 'feed'" class="feed">
            <div class="workout-streak">
              <div v-if="authStore.user.userId == userInfo.user?.userId">
                <div v-if="!hasTodayPost" class="no-post-message">
                <p>아직 운동 인증을 하지 않았어요!</p>
                  <button class="btn" @click="showUploadModal">#오운완 하러가기</button>
                </div>
                <div v-else class="post-completed">
                  <p>오늘도 운동을 완료했어요.</p>
                  <button v-if="userInfo.canSlot" role="button" class="golden-button" @click="showSlotMachine">
                    <span class="golden-text">I'm feeling Lucky</span>
                  </button>
                </div>
                <SlotMachineView v-if="isSlotMachineVisible"/>
              </div>

              <h3>{{ userInfo.user?.userNickname }} 님은 올해 {{ userInfo.myAllPost?.length }} 일 째 운동중!</h3>
              <UserStreakComponent :user_id="userId" />
            </div>
            <div class="my-routine">
              <h3>운동 루틴</h3>
              <div class="routine-buttons" v-if="isCurrentUser">
                <RouterLink :to="{ name: 'RoutineInfoComponent' }" class="btn see-all-routine">전체 보기</RouterLink>
                <RouterLink :to="{ name: 'CreateRoutineComponent' }" class="btn add-routine">운동 추가하기</RouterLink>
              </div>
              <div class="routine-component-container">
                <div class="routine-component" v-for="routine in userInfo.routines.slice(0, 3)" :key="routine.routineId">
                  <div class="routine-header">
                    <h4 class="routine-name">{{ routine.routineName }}</h4>
                    <p class="routine-date">{{ formatDate(routine.routineRegDate) }}</p>
                  </div>
                  <div class="routine-exercises">
                    <span v-for="exercise in routine.exercises" :key="exercise.exerciseId" class="exercise-item">
                      {{ exercise.exerciseName }}
                    </span>
                  </div>
                  <div class="routine-footer">
                    <span class="total-weight">총 중량: {{ calculateTotalWeight(routine.exercises) }} kg</span>
                  </div>
                </div>
              </div>
              <div v-if="userInfo.routines.length === 0">
                등록된 루틴이 없습니다.
              </div>
            </div>
            <h3>#오운완</h3>
            <div class="social-mypost">
              <div class="post-image" v-for="post in userInfo.myPost" :key="post.board.boardId">
                <img :src=" `/`+post.board.boardImgUrl" class="post-image" />
              </div>
            </div>
            <div v-if="userInfo.myPost?.length === 0">
              등록된 오운완이 없습니다.
            </div>
          </div>


          <div v-if="selectedTab === 'analysis'" class="analysis">
            <p>보유 스트릭 변경권: {{ userInfo.user?.userStreakPrice }}</p>
            <div class="slot-machine-container">
              <button role="button" class="press" @click="drawStreakColor" :disabled="userInfo.user?.userStreakPrice <= 0">
                <span class="press-text">{{ buttonText }}</span>
              </button>

              <div v-if="drawnColor" class="result">
                <div :class="['color-box', drawnColor]">
                  <template v-if="drawnColor === 'joker'">
                    <img src="/src/assets/img/thing.png" alt="Joker Image" class="joker-image" />
                  </template>
                  <span v-else class="color-name">{{ drawnColor }}</span>
                </div>
                <p v-if="drawnColor === 'joker'">앗! 야생의 띵균쌤(이)가 나타났다!</p>
              </div>
            </div>
          </div>




          <div v-if="selectedTab === 'setting'" class="setting">
            <div class="row">
              <div class="col-auto">
                <img :src="profileImageUrl || authStore.user.userProfileImage" @click="changeProfileImg" class="profile-image object-fit-cover">
                <input type="file" ref="profileImageInput" @change="onProfileImageChange" style="display: none;">
              </div>
              <div class="col">
                <div class="row">
                  <label for="changeNickname">닉네임</label>
                  <input type="text" id="changeNickname" v-model="newNickname">
                </div>
                <div class="row">
                  <div class="col-2">
                    <button @click="saveProfile" class="btn save-profile-btn">등록</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="point-history">
              <h4>포인트 내역</h4>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Upload Modal -->
    <div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="uploadModalLabel">Upload Image</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <label class="visibility-toggle">
              <input type="checkbox" v-model="writeBoard.boardVisibility">
              <svg viewBox="0 0 576 512" height="1em" xmlns="http://www.w3.org/2000/svg" class="lock-open">
                <path d="M352 144c0-44.2 35.8-80 80-80s80 35.8 80 80v48c0 17.7 14.3 32 32 32s32-14.3 32-32V144C576 64.5 511.5 0 432 0S288 64.5 288 144v48H64c-35.3 0-64 28.7-64 64V448c0 35.3 28.7 64 64 64H384c35.3 0 64-28.7 64-64V256c0-35.3-28.7-64-64-64H352V144z"></path>
              </svg>
              <svg viewBox="0 0 448 512" height="1em" xmlns="http://www.w3.org/2000/svg" class="lock">
                <path d="M144 144v48H304V144c0-44.2-35.8-80-80-80s-80 35.8-80 80zM80 192V144C80 64.5 144.5 0 224 0s144 64.5 144 144v48h16c35.3 0 64 28.7 64 64V448c0 35.3-28.7 64 64 64H64c-35.3 0-64-28.7-64-64V256c0-35.3 28.7-64 64-64H80z"></path>
              </svg>
            </label>
            <div class="routine-list-container">
              <div class="routine-list">
                <div v-for="routine in userInfo.routines" :key="routine.routineId" class="form-check">
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
              <label for="file" class="labelFile">
                <span>
                  <svg xml:space="preserve" viewBox="0 0 184.69 184.69" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg" id="Capa_1" version="1.1" width="60px" height="60px">
                    <g>
                      <g>
                        <g>
                          <path d="M149.968,50.186c-8.017-14.308-23.796-22.515-40.717-19.813C102.609,16.43,88.713,7.576,73.087,7.576c-22.117,0-40.112,17.994-40.112,40.115c0,0.913,0.036,1.854,0.118,2.834C14.004,54.875,0,72.11,0,91.959c0,23.456,19.082,42.535,42.538,42.535h33.623v-7.025H42.538c-19.583,0-35.509-15.929-35.509-35.509c0-17.526,13.084-32.621,30.442-35.105c0.931-0.132,1.768-0.633,2.326-1.392c0.555-0.755,0.795-1.704,0.644-2.63c-0.297-1.904-0.447-3.582-0.447-5.139c0-18.249,14.852-33.094,33.094-33.094c13.703,0,25.789,8.26,30.803,21.04c0.63,1.621,2.351,2.534,4.058,2.14c15.425-3.568,29.919,3.883,36.604,17.168c0.508,1.027,1.503,1.736,2.641,1.897c17.368,2.473,30.481,17.569,30.481,35.112c0,19.58-15.937,35.509-35.52,35.509H97.391v7.025h44.761c23.459,0,42.538-19.079,42.538-42.535C184.69,71.545,169.884,53.901,149.968,50.186z" style="fill:#010002;"></path>
                        </g>
                        <g>
                          <path d="M108.586,90.201c1.406-1.403,1.406-3.672,0-5.075L88.541,65.078c-0.701-0.698-1.614-1.045-2.534-1.045l-0.064,0.011c-0.018,0-0.036-0.011-0.054-0.011c-0.931,0-1.85,0.361-2.534,1.045L63.31,85.127c-1.403,1.403-1.403,3.672,0,5.075c1.403,1.406,3.672,1.406,5.075,0L82.296,76.29v97.227c0,1.99,1.603,3.597,3.593,3.597c1.979,0,3.59-1.607,3.59-3.597V76.165l14.033,14.036C104.91,91.608,107.183,91.608,108.586,90.201z" style="fill:#010002;"></path>
                        </g>
                      </g>
                    </g>
                  </svg>
                </span>
              </label>
              <input class="input" name="text" id="file" type="file" @change="onFileChange" />
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
  </div>



      <!-- Followers Modal -->
      <div class="modal fade" id="followersModal" tabindex="-1" aria-labelledby="followersModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="followersModalLabel">팔로워 목록</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <ul class="list-group">
              <li class="list-group-item" v-for="f in userInfo.follower" :key="f.user_id" @click="goProfile(f.user_id)">
                <img :src="f.user_profileImage" alt="Profile Image" class="follower-img">
                <span>{{ f.user_nickname }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Following Modal -->
    <div class="modal fade" id="followingModal" tabindex="-1" aria-labelledby="followingModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="followingModalLabel">팔로잉 목록</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <ul class="list-group">
              <li class="list-group-item" v-for="following in userInfo.following" :key="following.userId" @click="goProfile(following.following_user_id)">
                <img :src="following.userProfileImage" alt="Profile Image" class="following-img">
                <span>{{ following.userNickname }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { Modal } from 'bootstrap';
import { useSocialStore } from '@/stores/social';
import { useAuthStore } from '@/stores/auth';
import { useRoute } from 'vue-router';
import axios from 'axios';
import UserStreakComponent from '@/components/user/UserStreakComponent.vue';
import SlotMachineView from './SlotMachineView.vue';
import router from '@/router';

const socialStore = useSocialStore();
const authStore = useAuthStore();
const route = useRoute();
const isFollower = ref(false);

const selectedTab = ref('feed');
const selectTab = (tab) => {
  selectedTab.value = tab;
};

const userId = route.params.id;
const userInfo = ref({
  user: null,
  following: [],
  follower: [],
  routines: [],
  myPost: null,
  myAllPost: null,
  canSlot: true
});
const writeBoard = ref({
  userId: authStore.user.userId,
  routineId: 0,
  boardContent: '',
  boardVisibility: false,
});

const newNickname = ref(authStore.user.userNickname);
let uploadModal = null;
const imageUrl = ref(null);
const file = ref(null);

const isCurrentUser = computed(() => authStore.user.userId == userId);

// 유저정보
const getUserInfo = () => {
  axios.get(`http://localhost:8080/users/${userId}`).then((response) => {
    userInfo.value.user = response.data;
  });
};

// 팔로잉, 팔로워
const getFollowingFollowers = () => {
  axios.get(`http://localhost:8080/follows/following/${userId}`).then((response) => {
    userInfo.value.following = response.data;
  });

  axios.get(`http://localhost:8080/follows/followers/${userId}`).then((response) => {
    userInfo.value.follower = response.data;
    for(var i =0;i< userInfo.value.follower.length ;i++) {
      // if(userInfo.value.follower[i].user_id =)
    }
    isFollower.value = userInfo.value.follower.some(f => f.user_id === authStore.user.userId);
    console.log(userInfo.value.follower)
  });
};

//팔로우, 언팔로우 하기
const followUnfollow = () => {
  const myId = authStore.user.userId;
  axios.post('http://localhost:8080/follows/', { followingUserId: userId, userId: myId })
    .then((response) => {
      isFollower.value = !isFollower.value;
      if (isFollower.value) {
        userInfo.value.follower.push({ userId: myId });
      } else {
        userInfo.value.follower = userInfo.value.follower.filter(f => f.userId !== myId);
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

//팔로우, 팔로워 리스트 보기
const showFollowersModal = () => {
  const modalElement = document.getElementById('followersModal');
  const followersModal = new Modal(modalElement);
  followersModal.show();
};

const showFollowingModal = () => {
  const modalElement = document.getElementById('followingModal');
  const followingModal = new Modal(modalElement);
  followingModal.show();
};


// 루틴보기
const getRoutines = () => {
  axios.get(`http://localhost:8080/routines/users/${userId}`).then((response) => {
    userInfo.value.routines = response.data.map((routine) => ({
      ...routine,
      exercises: routine.exercises || [],
    }));
  });
};

// 작성글 보기
const getUserPost = () => {
  axios.get(`http://localhost:8080/boards/user/${userId}`).then((response) => {
    userInfo.value.myPost = response.data;
  });
};

//스트릭 기록 용 글 정보 보기 
const getAllPost = () => {
  axios.get(`http://localhost:8080/boards/user/private/${userId}`)
  .then((response) => {
    userInfo.value.myAllPost = response.data;
  })
}

// 오늘 운동 했는지 안했는지 확인
const hasTodayPost = computed(() => {
  const today = new Date().toLocaleDateString('en-CA', { timeZone: 'Asia/Seoul' });
  return userInfo.value.myAllPost?.some((post) => {
    const postDate = new Date(post.boardRegDate).toISOString().split('T')[0];
    return postDate === today;
  });
});

// 글 작성 모달 열기
const showUploadModal = () => {
  if (!uploadModal) {
    const modalElement = document.getElementById('uploadModal');
    uploadModal = new Modal(modalElement);
  }
  uploadModal.show();
};

// 사진 첨부
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

// 글 등록하기
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
  }).then(() => {
    file.value = null;
    imageUrl.value = null;
    writeBoard.value.routineId = 0;
    writeBoard.value.boardContent = '';
    writeBoard.value.boardVisibility = false;
    hideUploadModal();
  }).catch((err) => {
    console.error('Error registering post:', err);
  });
};

const hideUploadModal = () => {
  if (uploadModal) {
    uploadModal.hide();
  }
};

const profileImageUrl = ref(null);
const profileImageFile = ref(null);
const profileImageInput = ref(null);

const changeProfileImg = () => {
  profileImageInput.value.click();
};

const onProfileImageChange = (e) => {
  const file = e.target.files[0];
  if (file && file.type.startsWith('image/')) {
    profileImageFile.value = file;
    profileImageUrl.value = URL.createObjectURL(file);
  } else {
    profileImageFile.value = null;
    profileImageUrl.value = null;
  }
};

const saveProfile = () => {
  const formData = new FormData();

  if (profileImageFile.value) {
    formData.append('profileImage', profileImageFile.value);
  } else {
    formData.append('profileImage', '');
  }

  formData.append('userId', authStore.user.userId);
  formData.append('nickname', newNickname.value);

  axios.post('http://localhost:8080/users/update', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }).then(response => {
    profileImageFile.value = null;
    profileImageUrl.value = null;
    sessionStorage.setItem('user', JSON.stringify(response.data));
  }).catch(err => {
    console.error('Error updating profile:', err);
  });
};

const formatDate = (date) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(date).toLocaleDateString(undefined, options);
};

const calculateTotalWeight = (exercises) => {
  return exercises.reduce((total, exercise) => total + exercise.routineComponentsWeight * exercise.routineComponentsReps, 0);
};

//슬롯 머신 보이기
const isSlotMachineVisible = ref(false);

//골드 버튼 클릭
const showSlotMachine = () => {
  isSlotMachineVisible.value = true;
};

//스트릭 관련 확률
const streakColors = [
  { color: 'pink', probability: 22.25 },
  { color: 'black', probability: 22.25 },
  { color: 'green', probability: 22.25 },
  { color: 'blue', probability: 22.25 },
  { color: 'gold', probability: 10 },
  { color: 'joker', probability: 3 }
];

const drawnColor = ref(null);
const buttonText = ref('사용하기');

//스트릭 뽑는 로직
const drawStreakColor = async () => {
  if (userInfo.value.user.userStreakPrice <= 0) {
    alert("변경권이 부족합니다.");
    return;
  }

  // 스트릭 변경권 사용
  userInfo.value.user.userStreakPrice -= 1;
  buttonText.value = '다시 뽑기';

  const random = Math.random() * 100;
  let cumulativeProbability = 0;

  for (let streak of streakColors) {
    cumulativeProbability += streak.probability;
    if (random < cumulativeProbability) {
      drawnColor.value = streak.color;
      break;
    }
  }

  try {
    // 서버에 변경권 사용을 알림
    axios.get(`http://localhost:8080/users/streakColor/${userId}?userStreakColor=${drawnColor.value}`)
    .then((response) => {
      axios.get(`http://localhost:8080/users/streakprice/${userId}?userStreakPrice=-1`)
      .then((response) => {
        console.log("ddd")
      })
    })
  } catch (error) {
    console.error("An error occurred while using the streak price:", error);
    // 에러 발생 시 보유 변경권 개수를 원상복구
    userInfo.user.userStreakPrice += 1;
  }
};

const getMyCoupon = () => {
  axios.get(`http://localhost:8080/points/desc/${userId}`)
    .then((response) => {
      const data = response.data;
      const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

      let hasTodayEntry = false;

      for (let i = 0; i < data.length; i++) {
        const entryDate = data[i].pointRegDate.split('T')[0]; // Extract the date part
        if (entryDate === today) {
          console.log(data[i].pointRegDate)
          hasTodayEntry = true;
          break;
        }
      }

      if (hasTodayEntry) {
        console.log('asfdsfa')
        userInfo.value.canSlot = false
      }
    })
    .catch((error) => {
      console.error("An error occurred while fetching the data:", error);
    });
}

//프로필 이동
const goProfile = (user_id) => {
  window.location.href('http://localhost:5173/user/'+user_id)
  router.push({ name: 'MyPageView', params: { id: user_id } });
}


onMounted(() => {
  getUserPost();
  getAllPost();
  getFollowingFollowers();
  getUserInfo();
  getRoutines();
  getMyCoupon()
  console.log(userInfo.value.canSlot)
});
</script>


<style scoped>
/* General Styles */
.profile-page {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  background-color: #f9f9f9;
}
.tabs {
  display: flex;
  justify-content: space-around;
  background-color: #eaeaea;
  padding: 0.5rem 0;
  border-radius: 8px;
}
.tab {
  cursor: pointer;
  padding: 0.5rem 1rem;
  transition: background-color 0.3s, color 0.3s;
}
.tab.active {
  font-weight: bold;
  border-bottom: 2px solid #3297CE;
  color: #3297CE;
}
.main-content {
  display: flex;
}
.profile {
  flex: 0 0 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1rem;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-right: 1rem;
}
.profile-picture {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background-color: #ddd;
}
.nickname {
  margin-top: 1rem;
  font-size: 1.5rem;
}
.content-area {
  flex: 1;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.content {
  padding: 1rem;
}

.modal-body ul {
  padding: 0;
  list-style: none;
}
.list-group-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 1.25rem;
  border: 1px solid #ddd;
  margin-bottom: 0.5rem;
  border-radius: 5px;
}
.follower-img, .following-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 1rem;
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

.workout-streak h3 {
  margin-top: 10px;
}
.no-post-message {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffefef;
  padding: 1rem;
  border-radius: 10px;
  margin-bottom: 1rem;
  color: #ff6b6b;
}
.no-post-message button {
  background-color: #ff6b6b;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.no-post-message button:hover {
  background-color: #e55e5e;
}
.post-completed {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff2c6;
  padding: 1rem;
  border-radius: 10px;
  margin-bottom: 1rem;
  color: #947100;
}
.my-routine {
  margin-top: 2rem;
}
.routine-buttons {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
  gap: 1rem;
}
.routine-buttons .btn {
  background-color: #3297ce;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}
.routine-buttons .btn:hover {
  background-color: #2678b5;
  transform: scale(1.05);
}
.routine-component-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}
.routine-component {
  background-color: #f9f9f9;
  padding: 1rem;
  border-radius: 10px;
  margin-bottom: 1rem;
  flex: 1;
  min-width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.routine-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}
.routine-name {
  font-size: 1.25rem;
  font-weight: bold;
}
.routine-date {
  font-size: 0.875rem;
  color: #888;
}
.routine-exercises {
  margin-bottom: 0.5rem;
}
.exercise-item {
  display: inline-block;
  background-color: #e0f7fa;
  color: #006064;
  padding: 0.25rem 0.5rem;
  border-radius: 5px;
  margin: 0.25rem;
  font-size: 0.875rem;
}
.total-weight {
  font-weight: bold;
  color: #006064;
}
.social-mypost {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}
.social-mypost .post-image {
  flex: 0 0 calc(33.3333% - 1rem);
  width: 100%;
  border-radius: 10px;
  transition: transform 0.3s;
}
.social-mypost .post-image:hover {
  transform: scale(1.02);
}
.setting .save-profile-btn {
  background-color: #3297ce;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}
.setting .save-profile-btn:hover {
  background-color: #2678b5;
  transform: scale(1.05);
}
.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  cursor: pointer;
}
.profile-image:hover {
  opacity: 0.8;
}
.save-profile-btn {
  background-color: #4caf50;
  color: #fff;
}
.save-profile-btn:hover {
  background-color: #45a049;
}


.golden-button {
  touch-action: manipulation;
  display: inline-block;
  outline: none;
  font-family: inherit;
  font-size: 1em;
  box-sizing: border-box;
  border: none;
  border-radius: 0.3em;
  height: 2.75em;
  line-height: 2.5em;
  text-transform: uppercase;
  padding: 0 1em;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(110, 80, 20, 0.4),
    inset 0 -2px 5px 1px rgba(139, 66, 8, 1),
    inset 0 -1px 1px 3px rgba(250, 227, 133, 1);
  background-image: linear-gradient(
    160deg,
    #a54e07,
    #b47e11,
    #fef1a2,
    #bc881b,
    #a54e07
  );
  border: 1px solid #a55d07;
  color: rgb(120, 50, 5);
  text-shadow: 0 2px 2px rgba(250, 227, 133, 1);
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  background-size: 100% 100%;
  background-position: center;
}

.golden-button:focus,
.golden-button:hover {
  background-size: 150% 150%;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23),
    inset 0 -2px 5px 1px #b17d10, inset 0 -1px 1px 3px rgba(250, 227, 133, 1);
  border: 1px solid rgba(165, 93, 7, 0.6);
  color: rgba(120, 50, 5, 0.8);
}

.golden-button:active {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(110, 80, 20, 0.4),
    inset 0 -2px 5px 1px #b17d10, inset 0 -1px 1px 3px rgba(250, 227, 133, 1);
}


.slot-machine-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.press {
  background-color: gold;
  color: black;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  transition: background-color 0.3s;
}

.press:hover {
  background-color: darkgoldenrod;
}

.press-text {
  color: black;
}

.result {
  margin-top: 20px;
  text-align: center;
}

.color-box {
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 10px auto;
  border-radius: 50%;
  font-size: 16px;
  font-weight: bold;
  color: white;
}

.pink {
  background-color: pink;
}

.black {
  background-color: black;
}

.green {
  background-color: green;
}

.blue {
  background-color: rgba(37, 132, 255, 0.644);
}

.gold {
  background-color: rgb(255, 229, 80);
  color: black;
}

.joker {
  background: linear-gradient(45deg, red, blue, green, yellow, pink, black);
  background-size: 400%;
  animation: gradient 5s ease infinite;
}

@keyframes gradient {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.color-name {
  text-transform: capitalize;
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
</style>
