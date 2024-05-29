import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'



export const useAuthStore = defineStore('auth', () => {
  const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID
  const redirectUri = import.meta.env.VITE_GOOGLE_REDIRECT_URL
  const user = ref(JSON.parse(sessionStorage.getItem('user')))
  const router = useRouter()

  function loginWithGoogle() {
    const url = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&scope=openid email profile https://www.googleapis.com/auth/youtube.force-ssl`;
    window.location.href = url;
  }

  async function fetchUserOpenId(code) {
    try {
      const response = await axios.post('http://localhost:8080/oauth2/callback', {
        code: code,
        redirect_uri: redirectUri
      }, {
        withCredentials: true // 세션 정보를 주고 받기 위해 설정
      }).then((response) => {
        sessionStorage.setItem('user', JSON.stringify(response.data));
        user.value = response.data
        console.log(user.value)
        router.push("/")
      })
      // user.value = response.data;

      console.log(" 성공!!!@@@@@@@@@@@@@@@@@")
    } catch (error) {
      console.error('Failed to fetch user OpenID:', error);
    }
  }

  function logout() {
    user.value = null
    sessionStorage.removeItem('user')
    router.push('/') // 로그인 페이지로 이동
  }

  return { loginWithGoogle, fetchUserOpenId, logout, user: computed(() => user.value) }
})
