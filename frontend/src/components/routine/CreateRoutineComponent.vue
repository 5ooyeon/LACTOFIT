<template>
  <div class="create-routine-container">
    <h3>루틴 등록하기</h3>
    <div class="create-routine-header">
      <input type="text" class="form-control" placeholder="루틴 이름" v-model="routine.routineName">
      <textarea class="form-control" placeholder="루틴 설명" v-model="routine.routineDesc"></textarea>
    </div>
    <div class="create-routine-content">
      <div class="selected-exercise-section">
        <h3>선택한 운동</h3>
        <ul class="selected-exercise-list">
          <li 
            v-for="(exercise, index) in selectedExercises" 
            :key="exercise.exerciseId" 
            class="selected-exercise-item" 
            :class="exercise.exercisePart"
            draggable="true" 
            @dragstart="onDragStart($event, index)"
            @dragover="onDragOver($event, index)"
            @drop="onDrop($event, index)"
          >
            <span class="exercise-name">{{ exercise.exerciseName }}</span>
            <span class="exercise-part">({{ exercise.exercisePart }})</span>
            <div class="exercise-input-container">
              <input type="number" placeholder="횟수" v-model="exercise.reps" class="exercise-input" min="0"/>
              <input type="number" placeholder="중량" v-model="exercise.weight" class="exercise-input" min="0"/>
            </div>

            <span class="remove-exercise" @click="removeFromRoutine(exercise.exerciseId)">&#x2716;</span>
          </li>
        </ul>
        <button class="btn save-routine" @click="saveRoutine">저장하기</button>
      </div>

      <div class="search-exercise-section">
        <h3>최근 검색한 운동</h3>
        <ul class="recent-search-list">
          <li v-for="exercise in recentSearches" :key="exercise" :class="exercise.exercisePart" @click="addInRoutineByName(exercise.exerciseName)">
            {{ exercise.exerciseName }}
          </li>
        </ul>
        <input type="text" class="exercise-search-bar" placeholder="운동명으로 검색" @input="handleInput">
        
        <div class="category-buttons">
          <label v-for="category in categories" :key="category" :class="[category, {'active': selectedCategories.includes(category)}]">
            <input type="checkbox" @change="toggleCategory(category)" :checked="selectedCategories.includes(category)" />
            {{ category }}
          </label>
          <button class="clear-categories" @click="clearCategories">전체 해제</button>
        </div>
        
        <div v-for="(exercises, part) in filteredExerciseList" :key="part" class="exercise-group">
          <h3>[ {{ part }} ]</h3>
          <div class="exercise-row">
            <!-- <div v-for="(exerciseChunk, index) in chunkArray(exercises, 3)" :key="index" class="exercise-column"> -->
              <div v-for="exercise in exercises" :key="exercise.exerciseId" :class="exercise.exercisePart" class="exercise-item">
                <p @click="addInRoutine(exercise)">{{ exercise.exerciseName }}</p>
              </div>
            <!-- </div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios';
import { useAuthStore } from '@/stores/auth'
import Cookies from 'js-cookie';
import dayjs from 'dayjs';

const user = useAuthStore().user

const routine = ref({
  routineName: '',
  routineDesc: '',
  userId: user.userId
})

const exerciseList = ref([])
const keyword = ref("")
const selectedRoutine = ref([])
const dragIndex = ref(null)
const selectedCategories = ref([])

const categories = ["가슴", "등", "어깨", "전신", "코어", "팔", "하체"]

// 쿠키에서 최근 검색한 운동 가져오기
const getRecentSearches = () => {
  const recentSearches = Cookies.get('recentSearches');
  return recentSearches ? JSON.parse(recentSearches) : [];
}

const recentSearches = ref(getRecentSearches());

// 모든 운동 가져오기
const getAllExercises = () => {
  axios.get(`http://localhost:8080/exercises/`)
    .then((response) => {
      exerciseList.value = response.data
    })
    .catch((err) => {
      console.log(err);
    });
}

// 검색어로 운동 검색
const searchExercises = (keyword) => {
  axios.get(`http://localhost:8080/exercises/search?keyword=${keyword}`)
    .then((response) => {
      exerciseList.value = response.data
    })
    .catch((err) => {
      console.log(err);
    });
}

// 입력 이벤트 핸들러
const handleInput = (event) => {
  keyword.value = event.target.value;
  if (keyword.value) {
    searchExercises(keyword.value)
  } else {
    getAllExercises()
  }
}

// 카테고리 필터링
const toggleCategory = (category) => {
  const index = selectedCategories.value.indexOf(category);
  if (index === -1) {
    selectedCategories.value.push(category);
  } else {
    selectedCategories.value.splice(index, 1);
  }
}

// 선택한 카테고리 해제
const clearCategories = () => {
  selectedCategories.value = [];
}

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

// 선택된 카테고리 운동 목록 필터링
const filteredExerciseList = computed(() => {
  if (selectedCategories.value.length === 0) {
    return groupedExerciseList.value;
  } else {
    return Object.keys(groupedExerciseList.value).reduce((acc, key) => {
      if (selectedCategories.value.includes(key)) {
        acc[key] = groupedExerciseList.value[key];
      }
      return acc;
    }, {});
  }
});

// 배열을 주어진 크기로 나누기
const chunkArray = (array, chunkSize) => {
  const result = [];
  for (let i = 0; i < array.length; i += chunkSize) {
    result.push(array.slice(i, i + chunkSize));
  }
  return result;
}

// 선택한 운동을 리스트에 넣거나 제거하기
const addInRoutine = (exercise) => {
  const index = selectedRoutine.value.findIndex(e => e.exerciseId === exercise.exerciseId);
  if (index === -1) {
    selectedRoutine.value.push(exercise);
  } else {
    selectedRoutine.value.splice(index, 1);
  }
}

// 운동 이름으로 선택한 운동에 추가하기
const addInRoutineByName = (exerciseName) => {
  const exercise = exerciseList.value.find(e => e.exerciseName === exerciseName);
  if (exercise) {
    addInRoutine(exercise);
  }
}

// 선택한 운동에서 제거하기
const removeFromRoutine = (exerciseId) => {
  const index = selectedRoutine.value.findIndex(e => e.exerciseId === exerciseId);
  if (index !== -1) {
    selectedRoutine.value.splice(index, 1);
  }
}

// 선택한 운동의 상세 정보 가져오기
const selectedExercises = computed(() => {
  return selectedRoutine.value.map(exercise => exercise);
});

//루틴 저장하기
const saveRoutine = () => {
  //루틴 이름 null확인
  if(routine.value.routineName == '') {
    let tempRoutineName = dayjs().add(9, 'hour').toISOString().split('T')
    routine.value.routineName = tempRoutineName[0] + ' ' + tempRoutineName[1].split('.')[0] + ' 오늘의 루틴'
    console.log(routine.value.routineName)
  }

  //루틴 설명 null 확인
  if(routine.value.routineDesc == '') {
    routine.value.routineDesc = '오늘도 LactoFit으로 #오운완~'
  }

  if(selectedRoutine.value.length == 0) {
    alert('등록된 운동이 없습니다.')
    routine.value.routineDesc = ''
    routine.value.routineName = ''
    return;
  } 

  //먼저 루틴 등록하기
  axios.post('http://localhost:8080/routines/', routine.value)
  .then((response) => {
      saveExercisesInRoutine(response.data.routineId)
  })
  .catch((err) => {

  });
}

// 루틴에 운동 저장하기
const saveExercisesInRoutine = (routineId) => {
  const routineComponents = selectedRoutine.value.map(exercise => ({
    exerciseId: exercise.exerciseId,
    routineId: routineId,
    routineComponentsReps: exercise.reps || 0,
    routineComponentsWeight: exercise.weight || 0,
  }));
  // console.log(routineComponents)

  axios.post(`http://localhost:8080/exercises/${routineId}`, routineComponents)
    .then(() => {
      alert("운동이 루틴에 성공적으로 저장되었습니다.");
      window.location.href = 'http://localhost:5173/routine/myroutine'
    })
    .catch((err) => {
      console.error('Error adding exercises to routine:', err);
    });
}


// 드래그 앤 드롭 관련 메소드
const onDragStart = (event, index) => {
  dragIndex.value = index;
}

const onDragOver = (event, index) => {
  event.preventDefault();
}

const onDrop = (event, index) => {
  const draggedItem = selectedRoutine.value.splice(dragIndex.value, 1)[0];
  selectedRoutine.value.splice(index, 0, draggedItem);
}

onMounted(() => {
  getAllExercises();
});
</script>

<style scoped>
.create-routine-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.create-routine-header {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.form-control {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  transition: border-color 0.3s;
}

.form-control:focus {
  border-color: #3297CE;
}

.create-routine-content {
  display: flex;
  gap: 20px;
}

.selected-exercise-section,
.search-exercise-section {
  flex: 1;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.selected-exercise-section h3,
.search-exercise-section h3 {
  margin-bottom: 10px;
  color: #3297CE;
}

.selected-exercise-list,
.recent-search-list {
  list-style-type: none;
  padding: 0;
}

.selected-exercise-item,
.recent-search-list li {
  background-color: #f0f0f0;
  margin-bottom: 5px;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  align-items: center;
}

.selected-exercise-item .exercise-name {
  flex: 1;
}

.selected-exercise-item .exercise-part {
  margin-left: 10px;
  font-size: 0.9em;
  color: #666;
}

.exercise-input {
  width: 60px;
  margin: 0 5px;
  padding: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.exercise-input::-webkit-inner-spin-button,
.exercise-input::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.exercise-input[type="number"] {
  -moz-appearance: textfield;
}

.exercise-input-container {
  display: flex;
  align-items: center;
}

.exercise-input-container input {
  width: 50px;
  text-align: center;
}

.selected-exercise-item .remove-exercise {
  cursor: pointer;
  color: #ff0000;
  margin-left: 10px;
}

.selected-exercise-item:hover,
.recent-search-list li:hover {
  background-color: #e0e0e0;
}

.btn.save-routine {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #3297CE;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn.save-routine:hover {
  background-color: #2678b5;
}

.exercise-search-bar {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 20px;
}

.category-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.category-buttons label {
  padding: 5px 10px;
  border: 2px solid transparent;
  border-radius: 30px;
  /* background-color: #3297CE; */
  color: #fff;
  /* font-weight: bold; */
  cursor: pointer;
  
  transition: background-color 0.3s, border-color 0.3s, transform 0.3s ease;
}

.category-buttons label:hover {
  /* background-color: #2678b5; */
  transform: scale(1.1);
}

.category-buttons input[type="checkbox"] {
  display: none;
}

.category-buttons label.active {
  border-color: #000;
}

.category-buttons .가슴 {
  background-color: #ff8c00;
}

.category-buttons .등 {
  background-color: #00bfff;
}

.category-buttons .어깨 {
  background-color: #ff69b4;
}

.category-buttons .전신 {
  background-color: #32cd32;
}

.category-buttons .코어 {
  background-color: #ffa500;
}

.category-buttons .팔 {
  background-color: #9370db;
}

.category-buttons .하체 {
  background-color: #dc143c;
}

.clear-categories {
  padding: 5px 10px;
  background-color: whitesmoke;
  color: #000;
  border: 1px dotted #000;
  border-radius: 30px;
  cursor: pointer;
  margin-left: auto;
  transition: background-color 0.3s;
}

.clear-categories:hover {
  background-color: #444;
  color: #fff;
}

.exercise-group {
  margin-bottom: 20px;
}

.exercise-group h3 {
  margin-bottom: 10px;
  color: #3297CE;
}

.exercise-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.exercise-item {
  background-color: #f0f0f0;
  margin-right: 10px;
  padding: 7px 10px;
  border-radius: 5px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

.exercise-item:hover {
  background-color: #e0e0e0;
  transform: scale(1.05);
}

.exercise-item p {
  margin: 0;
}


.가슴 {
  border-left: 5px solid #ff8c00;
}

.등 {
  border-left: 5px solid #00bfff;
}

.어깨 {
  border-left: 5px solid #ff69b4;
}

.전신 {
  border-left: 5px solid #32cd32;
}

.코어 {
  border-left: 5px solid #ffa500;
}

.팔 {
  border-left: 5px solid #9370db;
}

.하체 {
  border-left: 5px solid #dc143c;
}
</style>
