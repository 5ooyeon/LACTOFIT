<template>
  <div class="my-routines-container">
    <h2>나의 루틴</h2>
    <RouterLink :to="{name: 'CreateRoutineComponent'}" class="create-routine-button">루틴 만들기</RouterLink>
    <div class="routine-list">
      <div 
        v-for="routine in myRoutines" 
        :key="routine.routineId" 
        class="routine-card"
        @click="toggleDetails(routine.routineId)"
      >
        <h3>{{ routine.routineName }}</h3>
        <p class="routine-date">등록 날짜: {{ formatDate(routine.routineRegDate) }}</p>
        <p class="routine-parts">
          운동 파트:
          <span v-for="(part, index) in getExerciseParts(routine)" :key="index">{{ part }}{{ index < getExerciseParts(routine).length - 1 ? ', ' : '' }}</span>
        </p>
        <p class="routine-total-weight">총 중량: {{ getTotalWeight(routine) }} kg</p>
        <div v-if="activeRoutineId === routine.routineId" class="routine-details">
          <h4>세부 정보</h4>
          <ul>
            <li v-for="exercise in routine.exercises" :key="exercise.exerciseId" :class="exercise.exercisePart">
              <p>운동명: {{ exercise.exerciseName }}</p>
              <p>운동 파트: {{ exercise.exercisePart }}</p>
              <p>횟수: {{ exercise.routineComponentsReps }}회</p>
              <p>중량: {{ exercise.routineComponentsWeight }} kg</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { RouterLink } from 'vue-router';
import axios from 'axios';
import dayjs from 'dayjs';

const myRoutines = ref([]);
const activeRoutineId = ref(null);

onMounted(() => {
  axios
    .get(`http://localhost:8080/routines/users/${useAuthStore().user.userId}`)
    .then((response) => {
      myRoutines.value = response.data;
      console.log(myRoutines.value)
    })
    .catch((err) => {
      console.error('Error fetching routines:', err);
    });
});

const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD');
}

const getExerciseParts = (routine) => {
  const parts = routine.exercises.map(exercise => exercise.exercisePart);
  return [...new Set(parts)];
}

const getTotalWeight = (routine) => {
  return routine.exercises.reduce((total, exercise) => total + (exercise.routineComponentsWeight * exercise.routineComponentsReps || 0), 0);
}

const toggleDetails = (routineId) => {
  activeRoutineId.value = activeRoutineId.value === routineId ? null : routineId;
}
</script>

<style scoped>
.my-routines-container {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 20px;
}

h2 {
  color: #3297CE;
  text-align: center;
}

.create-routine-button {
  display: block;
  width: fit-content;
  padding: 10px 20px;
  margin: 0 auto 20px;
  background-color: #3297CE;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
  text-align: center;
  transition: background-color 0.3s;
}

.create-routine-button:hover {
  background-color: #2678b5;
}

.routine-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.routine-card {
  flex: 1 1 calc(33.333% - 20px);
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.routine-card:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.routine-card h3 {
  margin-top: 0;
  color: #3297CE;
}

.routine-card p {
  color: #666;
  margin-bottom: 10px;
}

.routine-card .routine-date {
  font-size: 0.9em;
}

.routine-card .routine-parts {
  font-size: 0.9em;
}

.routine-card .routine-total-weight {
  font-size: 0.9em;
  font-weight: bold;
  color: #333;
}

.routine-details {
  margin-top: 10px;
}

.routine-details h4 {
  margin: 0;
  color: #3297CE;
}

.routine-details ul {
  list-style: none;
  padding: 0;
}

.routine-details li {
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-left: 5px solid;
}

.routine-details p {
  margin: 5px 0;
  color: #666;
}

/* 색상 설정 */
.가슴 {
  border-color: #ff8c00 !important;
}

.등 {
  border-color: #00bfff !important;
}

.어깨 {
  border-color: #ff69b4 !important;
}

.전신 {
  border-color: #32cd32 !important;
}

.코어 {
  border-color: #ffa500 !important;
}

.팔 {
  border-color: #9370db !important;
}

.하체 {
  border-color: #dc143c !important;
}
</style>
