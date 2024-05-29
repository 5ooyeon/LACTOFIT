<template>
  <div class="activity-calendar">
    <div class="calendar">
      <div class="calendar-grid">
        <div v-for="week in weeks" :key="week[0] ? week[0].toISOString() : Math.random()" class="week">
          <div
            v-for="day in week"
            :key="day ? day.toISOString() : Math.random()"
            :title="getTitle(day)"
            :class="['day', getColor(day), isToday(day) ? 'today' : '']"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watchEffect } from 'vue';
import { getYearWeeks, getDayOfYear, formatDate } from '../../utils/date';
import axios from 'axios';

// Accept user_id as a prop
const props = defineProps({
  user_id: {
    type: Number,
    required: true
  }
});

const year = ref(new Date().getFullYear());
const records = ref([]);

const myRoutines = () => {
  axios.get(`http://localhost:8080/boards/user/private/${props.user_id}`)
    .then((response) => {
      records.value = response.data;
    });
}

const weeks = ref(getYearWeeks(year.value));

const contributions = computed(() => {
  const contributionsMap = {};

  records.value.forEach(record => {
    const date = new Date(record.boardRegDate);
    const dayOfYear = getDayOfYear(date);
    if (!contributionsMap[dayOfYear]) {
      contributionsMap[dayOfYear] = [];
    }
    contributionsMap[dayOfYear].push(record);
  });

  return contributionsMap;
});

const streakColor = ref('');

axios.get(`http://localhost:8080/users/${props.user_id}`)
  .then((response) => {
    streakColor.value = response.data.userStreakColor;
    console.log(streakColor)
  });

function getColor(date) {
  if (!date) return 'color-0';
  const dayOfYear = getDayOfYear(date);
  const contributionCount = contributions.value[dayOfYear]?.length || 0;
  if (contributionCount >= 1) {
    return streakColor.value;
  }
  return 'color-0';
}

function getTitle(date) {
  if (!date) return '';
  const dayOfYear = getDayOfYear(date);
  return `${formatDate(date)}`;
}

function isToday(date) {
  if (!date) return false;
  const today = new Date(new Date().toLocaleString('en-US', { timeZone: 'Asia/Seoul' }));

  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
}

function setRandomColors() {
  const colors = ['pink', 'rgba(131, 202, 117, 0.719)', 'rgba(13, 146, 255, 0.616)', 'rgba(0, 0, 0, 0.452)', 'rgb(255, 221, 70)'];
  const jokerElements = document.querySelectorAll('.joker');
  jokerElements.forEach(element => {
    const randomColor = colors[Math.floor(Math.random() * colors.length)];
    element.style.backgroundColor = randomColor;
  });
}

onMounted(async () => {
  myRoutines();
  await nextTick();
  setRandomColors();
});

watchEffect(async () => {
  if (records.value.length > 0) {
    await nextTick();
    setRandomColors();
  }
});
</script>

<style scoped>
.activity-calendar {
  text-align: center;
}

.calendar {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.week-row {
  display: flex;
  justify-content: center;
}

.day-label {
  width: 15px;
  height: 15px;
  margin: 1px;
  text-align: center;
}

.calendar-grid {
  display: flex;
  flex-direction: row;
}

.week {
  display: flex;
  flex-direction: column;
  margin: 0.5px;
}

.day {
  width: 14px;
  height: 14px;
  margin: 0.5px;
  border-radius: 3px;
}

.color-0 {
  background-color: #ebedf0;
}

.pink {
  background-color: pink;
}

.green {
  background-color: rgba(131, 202, 117, 0.719);
}

.blue {
  background-color: rgba(13, 146, 255, 0.616);
}

.black {
  background-color: rgba(0, 0, 0, 0.452);
}

.gold {
  background-color: rgb(255, 221, 70);
  color: black;
}

.today {
  border: 2px solid rgb(255, 0, 98);
}
</style>
