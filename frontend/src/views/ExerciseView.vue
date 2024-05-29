
<template>
  <div class="container">
    <h1>운동 정보 검색</h1>
    <div id="answerbox" v-if="isLoading || response">
      <h3 class="selected-exercise">{{ selectedExercise }}</h3>
      <div v-if="isLoading" class="loader">
        <span class="l">L</span>
        <span class="o">A</span>
        <span class="a">C</span>
        <span class="d">T</span>
        <span class="i">O</span>
        <span class="n">F</span>
        <span class="g">I</span>
        <span class="d1">T</span>
        <span class="d2">!</span>
      </div>
      <div v-else>
        {{ response }}
      </div>
    </div>
    <div class="button-container" v-if="response && !isLoading">
      <RouterLink class="go-to-routine" type="button" :to="{name: 'CreateRoutineComponent'}">
        루틴 만들러 가기
      </RouterLink>
      <button class="go-to-youtube" @click="goYoutube()">관련 유튜브 영상 보기</button>
    </div>
    <input 
      type="text" 
      class="exercise-search-bar" 
      placeholder="운동명으로 검색" 
      @input="handleInput"
      ref="searchInput">
    <div v-for="(exercises, part) in groupedExerciseList" :key="part" class="exercise-group">
      <h3>[ {{ part }} ]</h3>
      <div class="exercise-grid">
        <div v-for="exercise in exercises" :key="exercise.exerciseId" class="exercise-item">
          <p @click="searchExercise(exercise.exerciseName)">{{ exercise.exerciseName }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';
import { useYoutubeStore } from '@/stores/youtube';
import { useRouter } from 'vue-router';
import Cookies from 'js-cookie';

const openaiApiKey = import.meta.env.VITE_OPENAI_API_KEY;

const user = useAuthStore().user;
const youtubeStore = useYoutubeStore();
const router = useRouter();

const routine = ref({
  routineName: '',
  routineDesc: '',
  userId: user.userId,
});

const exerciseList = ref([]);
const keyword = ref("");
const response = ref("");
const selectedRoutine = ref([]);
const isPressed = ref(false);
const selectedExercise = ref("");
const isLoading = ref(false);
const searchInput = ref('');

// 모든 운동 가져오기
const getAllExercises = () => {
  axios.get(`http://localhost:8080/exercises/`)
    .then((response) => {
      exerciseList.value = response.data;
      console.log(exerciseList.value)
    })
    .catch((err) => {
      console.log(err);
    });
};

// 검색어로 운동 검색
const searchExercises = (keyword) => {
  axios.get(`http://localhost:8080/exercises/search?keyword=${keyword}`)
    .then((response) => {
      exerciseList.value = response.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

// 입력 이벤트 핸들러
const handleInput = (event) => {
  keyword.value = event.target.value;
  if (keyword.value) {
    searchExercises(keyword.value);
  } else {
    getAllExercises();
  }
};

// 쿠키에 운동명 저장
const saveToCookies = (exerciseName) => {

  let recentSearches = Cookies.get('recentSearches');
  recentSearches = recentSearches ? JSON.parse(recentSearches) : [];

  //운동명으로 운동 정보 가져오는 axios
  axios.get(`http://localhost:8080/exercises/searchbyname?title=${exerciseName}`)
  .then((response ) => {

    if (!recentSearches.includes(response.data)) {
      recentSearches.push(response.data);
      if (recentSearches.length > 10) {
        recentSearches.shift(); // 최근 10개만 유지
      }
      Cookies.set('recentSearches', JSON.stringify(recentSearches), { expires: 7 });
    }

  })

};

// 운동에 대한 설명 검색
const searchExercise = async (exerciseName) => {
  selectedExercise.value = exerciseName;
  isLoading.value = true; // 로딩 상태 시작
  window.scrollTo({ top: 0, behavior: 'smooth' }); // 페이지 상단으로 스크롤
  searchInput.value.value = '';
  saveToCookies(exerciseName);
  getAllExercises();
  try {
    const prompt = `[${exerciseName}] 라는 운동에 대해 한국어와 존댓말로 다음과 같은 형식으로 답변해줘 .

    펙 덱 플라이는 가슴근육과 삼각근을 강화하는 운동입니다. 이 운동은 펙 덱 머신을 이용하여 수행됩니다. 

1단계: 먼저 펙 덱 머신에 앉은 후, 손잡이를 잡고 팔을 뻗어줍니다.
2단계: 가슴을 바닥에 수평하게 유지하면서 손을 몸 쪽으로 모으는 운동을 시작합니다.
3단계: 가슴을 최대한 벌리며 손을 다시 처음 자세로 돌아갑니다.
4단계: 숨을 내쉬면서 가동 범위를 최대한 확보합니다.
5단계: 팔을 뻗으면서 다시 시작 자세로 돌아옵니다.

이 운동을 10-15회 반복하고 3-4 세트를 수행하면 좋습니다. 근육에 무리가 가지 않도록 필요에 따라 중량을 조절해가며 운동을 하시기 바랍니다.

위는 내가 원하는 답변의 예시야
    `;
    const result = await fetchGPT35Response(prompt);
    response.value = result;
    isLoading.value = false; // 로딩 상태 종료
  } catch (error) {
    if (error.response && error.response.status === 429) {
      response.value = "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.";
      console.error("Too many requests - status code 429");
      await delay(3000); // 3초 대기 후 재시도
      searchExercise(exerciseName); // 재시도
    } else {
      response.value = "에러 발생";
      console.error(error);
    }
  }
};

const fetchGPT35Response = async (prompt) => {
  const response = await axios.post(
    "https://api.openai.com/v1/chat/completions",
    {
      model: "gpt-3.5-turbo",
      messages: [{ role: "user", content: prompt }],
    },
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${openaiApiKey}`,
      },
    }
  );

  return response.data.choices[0].message.content;
};

const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

// 운동 데이터를 부위별로 그룹화
const groupedExerciseList = computed(() => {
  return exerciseList.value.reduce((acc, exercise) => {
    if (!acc[exercise.exercisePart]) {
      acc[exercise.exercisePart] = [];
    }
    acc[exercise.exercisePart].push(exercise);
    return acc;
  }, {});
});

//검색한 운동과 유튜브 이동 메소드
const goYoutube = async () => {
  const keyword1 = selectedExercise.value;
  const search1 = 'search';
  await youtubeStore.getVideosBySearchWord(keyword1, search1);
  router.push({ name: 'SearchResultComponent' });
};

onMounted(getAllExercises);
</script>
<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 60px;
}

#answerbox {
  border: 1px solid #ddd;
  padding: 20px;
  margin-top: 20px;
  background-color: #fff;
  border-radius: 10px;
  overflow-y: auto;
  white-space: pre-wrap;
  max-width: 800px;
  margin: 20px auto;
  height: auto;
  max-height: 600px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.exercise-search-bar {
  width: 80%;
  padding: 15px;
  margin: 20px auto;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.exercise-group {
  margin-top: 30px;
}

.exercise-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.exercise-item {
  flex: 1 1 30%;
  margin: 10px;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.exercise-item:hover {
  background-color: #e6e6e6;
}

.loader {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.loader span {
  color: #3c90e2;
  opacity: 0;
  animation: fadeInOut 2s ease-in-out infinite;
  font-weight: bold;
  text-shadow: 2px 2px 3px #919191;
}

.loader span:nth-child(1) { animation-delay: 0.2s; }
.loader span:nth-child(2) { animation-delay: 0.4s; }
.loader span:nth-child(3) { animation-delay: 0.6s; }
.loader span:nth-child(4) { animation-delay: 0.8s; }
.loader span:nth-child(5) { animation-delay: 1s; }
.loader span:nth-child(6) { animation-delay: 1.2s; }
.loader span:nth-child(7) { animation-delay: 1.4s; }
.loader span:nth-child(8) { animation-delay: 1.6s; }
.loader span:nth-child(9) { animation-delay: 2s; }

@keyframes fadeInOut {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  max-width: 800px;
  margin: 20px auto;
}

.go-to-routine, .go-to-youtube {
  width: 49%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #3c90e2;
  color: white;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease;
  text-decoration: none;
}

.go-to-routine:hover, .go-to-youtube:hover {
  background-color: #2b6ba3;
}
</style>