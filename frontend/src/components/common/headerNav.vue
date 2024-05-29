<template>
  <header class="navbar navbar-expand-lg">
    <nav class="container">
      <div class="navbar-collapse d-lg-flex" id="navbarsExample11">
        <!-- 조건부 렌더링을 위한 템플릿 -->
        <template v-if="route.name === 'Main'">
          <a href="#" class="navbar-brand col-lg-3 me-0">
            <img src="../../assets/img/LACTOFIT_HEADER.png" class="logo-img" />
          </a>
        </template>
        <template v-else>
          <RouterLink :to="{ name: 'Main' }" class="navbar-brand col-lg-3 me-0">
            <img src="../../assets/img/LACTOFIT_HEADER.png" class="logo-img" />
          </RouterLink>
        </template>
        
        <ul class="navbar-nav col-lg-6 justify-content-lg-center">
          <li class="nav-item">
            <template v-if="route.name === 'Main'">
              <a class="nav-link" href="#routine">운동백과</a>
            </template>
            <template v-else>
              <RouterLink class="nav-link" :to="{name: 'ExerciseView'}">운동백과</RouterLink>
            </template>
          </li>
          <li class="nav-item">
            <template v-if="route.name === 'Main'">
              <a class="nav-link" href="#youtube">영상PICK</a>
            </template>
            <template v-else>
              <RouterLink class="nav-link" :to="{name: 'YoutubeMainComponent'}">영상PICK</RouterLink>
            </template>
          </li>
          <li class="nav-item">
            <template v-if="route.name === 'Main'">
              <a class="nav-link" href="#social">#오운완</a>
            </template>
            <template v-else>
              <RouterLink class="nav-link" :to="{name: 'SocialMainComponent'}">#오운완</RouterLink>
            </template>
          </li>
        </ul>
        <div class="d-lg-flex col-lg-3 justify-content-lg-end align-items-center">
          <button class="signin" @click="loginWithGoogle" v-if="!authStore.user">
            <img src="//cdn.casetify.com/img/ui/icon-account-google.svg" data-v-028a7b4d="" style="vertical-align: middle;">
            <span>Google 로그인</span>
          </button>
          <div v-if="authStore.user" style="display: flex;">
            <div class="notification dropstart me-3" type="button" data-bs-toggle="dropdown" aria-expanded="false" :class="{ 'has-unread-notifications': hasUnreadNotifications }">
              <div class="bell-container">
                <div class="bell"></div>
              </div>
              <ul class="dropdown-menu dropdown-menu-end p-3">
                <form class="form">
                  <p class="notiform-title">알림</p>
                  <div class="notifications-container scrollbar">
                    <div v-if="notifications.length !== 0">
                      <li v-for="noti in notifications" :key="noti.notificationId">
                        <div class="alert" :class="{ 'alert-read': noti.notificationRead }">
                          <div class="alert-prompt-wrap" @click="openModal(noti)">
                            <p class="text-sm text-yellow-700">
                              {{ noti.notificationContent }}
                              <p class="noti-regDate">{{ formatNotificationDate(noti.notificationRegDate) }}</p>
                            </p>
                          </div>
                          <button class="btn-delete" @click.stop="deleteNoti(noti.notificationId)">
                            <svg viewBox="0 0 15 17.5" height="17.5" width="15" xmlns="http://www.w3.org/2000/svg" class="icon-delete">
                              <path transform="translate(-2.5 -1.25)" d="M15,18.75H5A1.251,1.251,0,0,1,3.75,17.5V5H2.5V3.75h15V5H16.25V17.5A1.251,1.251,0,0,1,15,18.75ZM5,5V17.5H15V5Zm7.5,10H11.25V7.5H12.5V15ZM8.75,15H7.5V7.5H8.75V15ZM12.5,2.5h-5V1.25h5V2.5Z" id="Fill"></path>
                            </svg>
                          </button>
                        </div>
                      </li>
                    </div>
                    <div class="no-noti" v-else>
                      알림이 없습니다.
                    </div>
                  </div>
                  <button class="btn-delete-read" @click="deleteReadNotifications">
                    <span class="btn-delete-read__text">읽은 알림 삭제</span>
                    <span class="btn-delete-read__icon"><svg class="svg" height="512" viewBox="0 0 512 512" width="512" xmlns="http://www.w3.org/2000/svg"><title></title><path d="M112,112l20,320c.95,18.49,14.4,32,32,32H348c17.67,0,30.87-13.51,32-32l20-320" style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"></path><line style="stroke:#fff;stroke-linecap:round;stroke-miterlimit:10;stroke-width:32px" x1="80" x2="432" y1="112" y2="112"></line><path d="M192,112V72h0a23.93,23.93,0,0,1,24-24h80a23.93,23.93,0,0,1,24,24h0v40" style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"></path><line style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px" x1="256" x2="256" y1="176" y2="400"></line><line style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px" x1="184" x2="192" y1="176" y2="400"></line><line style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px" x1="328" x2="320" y1="176" y2="400"></line></svg></span>
                  </button>
                </form>
              </ul>
            </div>
            <div class="dropdown">
              <img :src="authStore.user.userProfileImage" class="user-profile-img object-fit-cover dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <ul class="dropdown-menu dropdown-menu-end p-3">
                <li><RouterLink :to="{ name: 'MyPageView', params: { id: authStore.user.userId } }" class="dropdown-item">나의 프로필</RouterLink></li>
                <li><button @click="logout()" class="dropdown-item">로그아웃</button></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </header>

  <!-- Modal -->
  <div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content form">
        <div class="modal-header">
          <h5 class="modal-title notiform-title" id="notificationModalLabel">알림</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p>{{ selectedNotification?.notificationContent }}</p>
          <p>{{ formatNotificationDate(selectedNotification?.notificationRegDate) }}</p>
        </div>
        <div class="modal-footer">
          <button class="btn-delete" @click.stop="deleteNoti(selectedNotification?.notificationId)">
            <svg viewBox="0 0 15 17.5" height="17.5" width="15" xmlns="http://www.w3.org/2000/svg" class="icon-delete">
              <path transform="translate(-2.5 -1.25)" d="M15,18.75H5A1.251,1.251,0,0,1,3.75,17.5V5H2.5V3.75h15V5H16.25V17.5A1.251,1.251,0,0,1,15,18.75ZM5,5V17.5H15V5Zm7.5,10H11.25V7.5H12.5V15ZM8.75,15H7.5V7.5H8.75V15ZM12.5,2.5h-5V1.25h5V2.5Z" id="Fill"></path>
            </svg>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth.js'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import utc from 'dayjs/plugin/utc'
import { Modal } from 'bootstrap'

dayjs.extend(relativeTime)
dayjs.extend(utc)

const authStore = useAuthStore()
const route = useRoute()
const router = useRouter()

const notifications = ref([])
const selectedNotification = ref(null)
let notificationModal = null

const loginWithGoogle = () => {
  authStore.loginWithGoogle()
}

const logout = () => {
  authStore.logout()
}

const goToProfilePage = () => {
  router.push({ name: 'UserProfile' })
}

const hasUnreadNotifications = ref(false)

const getNotifications = () => {
  const userId = authStore.user.userId;
  axios.get('http://localhost:8080/notifications/' + userId)
    .then((Response) => {
      notifications.value = Response.data;
      console.log(notifications.value);
      // Check if there are any unread notifications
      hasUnreadNotifications.value = notifications.value.some(noti => !noti.notificationRead);
    });
}

// 알림 삭제
const deleteNoti = (notificationId) => {
  axios.delete(`http://localhost:8080/notifications/${notificationId}`)
    .then(() => {
      notifications.value = notifications.value.filter(noti => noti.notificationId !== notificationId)
    })
}

// 읽은 알림 전체 삭제
const deleteReadNotifications = () => {
  const readNotifications = notifications.value.filter(noti => noti.notificationRead);
  readNotifications.forEach(noti => deleteNoti(noti.notificationId));
}

// 모달 열기
const openModal = (noti) => {
  selectedNotification.value = noti
  if (!notificationModal) {
    const modalElement = document.getElementById('notificationModal')
    notificationModal = new Modal(modalElement)
  }
  //알림 읽음 처리
  axios.put('http://localhost:8080/notifications/'+noti.notificationId)
  .then((response) => {
    noti.notificationRead = true;
    hasUnreadNotifications.value = notifications.value.some(noti => !noti.notificationRead);
    console.log('read')
  })
  notificationModal.show()
}

// 날짜 형식 변환 함수
const formatNotificationDate = (date) => {
  const now = dayjs().utc();
  // 한국 시간에서 UTC로 변환 (-9시간)
  const notificationDate = dayjs(date).subtract(9, 'hour').utc();
  const diffInMinutes = now.diff(notificationDate, 'minute');
  const diffInHours = now.diff(notificationDate, 'hour');

  if(diffInMinutes < 5) {
    return `방금 전`;
  } else if (diffInMinutes < 60) {
    return `${diffInMinutes}분 전`;
  } else if (diffInHours < 12) {
    return `${diffInHours}시간 전`;
  } else {
    return notificationDate.format('YYYY-MM-DD HH:mm');
  }
}

onMounted(() => {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const code = urlParams.get('code');

  if (authStore.user) {
    getNotifications();
  }
  console.log(notifications.value)
  if (code) {
    authStore.fetchUserOpenId(code).then(() => {
      if (authStore.user) {
        getNotifications();
      }
    });
  }
});
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  left: 0;
  width: 100%;
  background-color: white;
  z-index: var(--z-fixed);
  transition: .4s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 추가된 부분 */
}

.logo {
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
}

.logo-img {
  height: 100%; /* 부모 요소의 높이에 맞추기 */
  max-height: 60px; /* 최대 높이 설정 */
  width: auto; /* 비율 유지 */
}

.navbar-brand, .nav-link {
  color: #333;
}

.navbar-brand:hover, .nav-link:hover {
  color: #000;
}

/* 추가된 부분 */
.navbar-nav .nav-item + .nav-item {
  margin-left: 1rem; /* 간격을 원하는 대로 조정 */
}

.signin {
  max-width: 320px;
  display: flex;
  padding: 0.5rem 1.4rem;
  font-size: .9rem !important;
  line-height: 1.25rem;
  font-weight: 700;
  text-align: center;
  text-transform: uppercase;
  vertical-align: middle;
  align-items: center;
  border-radius: 0.5rem;
  border: 1px solid rgba(0, 0, 0, 0.25);
  gap: 0.75rem;
  color: rgb(65, 63, 63) !important;
  background-color: #fff;
  cursor: pointer;
  transition: all .6s ease;
}

.signin svg {
  height: 24px;
}

.signin:hover {
  transform: scale(1.02);
}

.svg__google {
  height: 24px;
  width: auto;
}

.user-profile-img {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  cursor: pointer;
  margin-left: 1rem; /* 추가된 부분 */
}

.dropdown-menu-end {
  right: 0;
  left: auto;
}

.bell-container {
  position: relative;
}

.bell {
  border: 2.17px solid black;
  border-radius: 10px 10px 0 0;
  width: 15px;
  height: 17px;
  background: transparent;
  display: block;
  position: relative;
  top: -3px;
}
.bell::before,
.bell::after {
  content: "";
  background: black;
  display: block;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  height: 2.17px;
}
.bell::before {
  top: 100%;
  width: 20px;
}
.bell::after {
  top: calc(100% + 4px);
  width: 7px;
}

.notification {
  background: transparent;
  border: none;
  padding: 0; /* 변경된 부분 */
  border-radius: 50px;
  cursor: pointer;
  transition: 300ms;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1rem; /* 추가된 부분 */
}

.notification::before {
  content: "";
  color: white;
  font-size: 10px;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: red;
  position: absolute;
  right: -4px;
  top: 3px;
  display: none; /* 기본적으로 숨김 */
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.has-unread-notifications::before {
  display: block; /* 안 읽은 알림이 있을 때만 표시 */
}

.notification:hover {
  background: rgba(170, 170, 170, 0.062);
}

.notification:hover > .bell-container {
  animation: bell-animation 650ms ease-out 0s 1 normal both;
}

@keyframes bell-animation {
  20% {
    transform: rotate(15deg);
  }

  40% {
    transform: rotate(-15deg);
    scale: 1.1;
  }
  60% {
    transform: rotate(10deg);
    scale: 1.1;
  }
  80% {
    transform: rotate(-10deg);
  }
  0%,
  100% {
    transform: rotate(0deg);
  }
}

.notifications-container {
  width: 320px;
  max-height: 300px; /* 고정된 높이 */
  font-size: 0.875rem;
  line-height: 1.25rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  overflow-y: scroll;
  margin: 1rem 0;
}

.flex-shrink-0 {
  flex-shrink: 0;
}

.alert {
  border-color: grey;
  border-radius: 20px;
  padding: 1rem;
  display: flex;
  margin-bottom: 0 !important;
  align-items: center; /* Ensure items are centered vertically */
  border: 1px solid #ccc;
}

.alert-svg {
  height: 1.25rem;
  width: 1.25rem;
}

.alert-prompt-wrap {
  margin-left: 0.75rem;
  flex-grow: 1; /* Allow it to take up the remaining space */
}

.alert-prompt-link {
  font-weight: 500;
  color: rgb(141, 56, 0);
  text-decoration: underline;
}

.alert-prompt-link:hover {
  color: rgb(202 138 4);
}

.alert button {
  margin-left: auto; /* Push the button to the right */
}

.alert-read {
  background-color: #f0f0f0;
}

.btn-delete {
  background-color: transparent;
  position: relative;
  border: none;
  cursor: pointer;
}

.icon-delete {
  transform: scale(1.2);
  transition: .2s linear;
}

.btn-delete:hover > .icon-delete {
  transform: scale(1.5);
}

.btn-delete:hover > .icon-delete path {
  fill: rgb(168, 7, 7);
}

.btn-delete-read {
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 150px;
  height: 40px;
  cursor: pointer;
  display: flex;
  align-items: center;
  border: 1px solid #cc0000;
  background-color: #e50000;
  overflow: hidden;
  border-radius:20px;
}

.btn-delete-read, .btn-delete-read__icon, .btn-delete-read__text {
  transition: all 0.3s;
}

.btn-delete-read .btn-delete-read__text {
  transform: translateX(12px);
  color: #fff;
  font-weight: 600;
  font-size: 13.5px;
}

.btn-delete-read .btn-delete-read__icon {
  position: absolute;
  transform: translateX(109px);
  height: 100%;
  width: 39px;
  background-color: #cc0000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-delete-read .svg {
  width: 20px;
}

.btn-delete-read:hover {
  background: #cc0000;
}

.btn-delete-read:hover .btn-delete-read__text {
  color: transparent;
}

.btn-delete-read:hover .btn-delete-read__icon {
  width: 148px;
  transform: translateX(0);
}

.btn-delete-read:active .btn-delete-read__icon {
  background-color: #b20000;
}

.btn-delete-read:active {
  border: 1px solid #b20000;
}

.form {
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  padding: 1rem;
  position: relative; /* Added */
  height: 468px;
}

.notiform-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.noti-regDate {
  margin-top: .5rem;
}

.signin a {
  color: #007bff;
  text-decoration: none;
}

.signin a:hover {
  text-decoration: underline;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 350px;
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
  position: relative;
}

.notiform-title {
  font-size: 28px;
  color: royalblue;
  font-weight: 600;
  letter-spacing: -1px;
  position: relative;
  display: flex;
  align-items: center;
  padding-left: 30px;
}

.notiform-title::before,.notiform-title::after {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  border-radius: 50%;
  left: 0px;
  background-color: royalblue;
}

.notiform-title::before {
  width: 18px;
  height: 18px;
  background-color: royalblue;
}

.notiform-title::after {
  width: 18px;
  height: 18px;
  animation: pulse 1s linear infinite;
}

.message, .signin {
  color: rgba(88, 87, 87, 0.822);
  font-size: 14px;
}

.signin {
  text-align: center;
}

.signin a {
  color: royalblue;
}

.signin a:hover {
  text-decoration: underline royalblue;
}

.flex {
  display: flex;
  width: 100%;
  gap: 6px;
}

.form label {
  position: relative;
}

.form label .input {
  width: 100%;
  padding: 10px 10px 20px 10px;
  outline: 0;
  border: 1px solid rgba(105, 105, 105, 0.397);
  border-radius: 10px;
}

.form label .input + span {
  position: absolute;
  left: 10px;
  top: 15px;
  color: grey;
  font-size: 0.9em;
  cursor: text;
  transition: 0.3s ease;
}

.form label .input:placeholder-shown + span {
  top: 15px;
  font-size: 0.9em;
}

.form label .input:focus + span,.form label .input:valid + span {
  top: 30px;
  font-size: 0.7em;
  font-weight: 600;
}

.form label .input:valid + span {
  color: green;
}

.submit {
  border: none;
  outline: none;
  background-color: royalblue;
  padding: 10px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: .3s ease;
}

.submit:hover {
  background-color: rgb(56, 90, 194);
}

@keyframes pulse {
  from {
    transform: scale(0.9);
    opacity: 1;
  }

  to {
    transform: scale(1.8);
    opacity: 0;
  }
}

/* Custom Scrollbar Styles for WebKit browsers */
.notifications-container::-webkit-scrollbar {
  width: 8px !important;
}
</style>
