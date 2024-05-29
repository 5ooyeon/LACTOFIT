<template>
  <div id="app">
    <form class="form">
      <p class="title" style="text-align: center;">스트릭 변경권 뽑기</p>
      <p class="message">나온 숫자 3개의 합만큼 변경권을 받을 수 있어요.</p>
      <div class="slots">
        <div class="slot" v-for="(slot, index) in slots" :key="index">
          <div class="number" v-for="(number, i) in slot.numbers.slice(0, 3)" :key="i">
            {{ number }}
          </div>
        </div>
      </div>
      <button class="submit" @click.prevent="toggleSlots">
        {{ isSpinning ? "STOP" : "START" }}
      </button>
      <div class="sticker-note" v-if="hasDuplicates">
        더블! 같은 수끼리는 곱한만큼 얻을 수 있어요.
      </div>
      <div class="price">
        <span>총 </span>
        <span>{{ sum }}</span>
        <span> 개의 스트릭 변경권을 얻었어요</span>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const slots = ref([
  { numbers: [], interval: null },
  { numbers: [], interval: null },
  { numbers: [], interval: null }
]);
const isSpinning = ref(false);
const sum = ref(0);
const hasDuplicates = ref(false);

const shuffle = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
};

const toggleSlots = async () => {
  const userId = useAuthStore().user.userId;

  const hasTodayEntry = await checkTodayEntry(userId);
  if (hasTodayEntry) {
    alert("이미 변경권을 얻었습니다.");
    location.reload(); 
    return;
  }

  if (isSpinning.value) {
    stopSlots();
  } else {
    startSlots();
  }
};

const checkTodayEntry = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8080/points/desc/${userId}`);
    const data = response.data;
    const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

    for (let i = 0; i < data.length; i++) {
      const entryDate = data[i].pointRegDate.split('T')[0]; // Extract the date part
      if (entryDate === today) {
        return true;
      }
    }
    return false;
  } catch (error) {
    console.error("An error occurred while fetching the data:", error);
    return false;
  }
};

const startSlots = () => {
  isSpinning.value = true;
  sum.value = 0;
  hasDuplicates.value = false;
  const intervalSpeed = 50;
  slots.value.forEach((slot) => {
    slot.numbers = shuffle(Array.from({ length: 10 }, (_, i) => i));
    slot.interval = setInterval(() => {
      slot.numbers.push(slot.numbers.shift());
    }, intervalSpeed);
  });
};

const stopSlots = () => {
  slots.value.forEach((slot) => {
    clearInterval(slot.interval);
  });
  isSpinning.value = false;

  calculateSum();
  saveSlotUsage();
};

const calculateSum = () => {
  let numberCounts = {};
  slots.value.forEach((slot) => {
    const number = slot.numbers[0];
    numberCounts[number] = (numberCounts[number] || 0) + 1;
  });
  sum.value = Object.keys(numberCounts).reduce((acc, number) => {
    return acc + Math.pow(parseInt(number), numberCounts[number]);
  }, 0);

  hasDuplicates.value = Object.values(numberCounts).some(count => count > 1);
  
  const userId = useAuthStore().user.userId;
  const userStreakPrice = sum.value
  
  //sum value 넘기기
  axios.get(`http://localhost:8080/users/streakprice/${userId}?userStreakPrice=${userStreakPrice}`)
  .then((response) => {
    console.log('됐다!')
  })
  .catch((err) => {
    console.log('Error!')
  })
};

const saveSlotUsage = () => {
  const userId = useAuthStore().user.userId;
  const point = {
    userId: userId,
    pointAmount: 1
  };

  axios.post('http://localhost:8080/points/', point)
  .then((response) => {
    console.log('Slot usage saved successfully:', response);
  })
  .catch((err) => {
    console.error('Error saving slot usage:', err);
  });
};
</script>

<style scoped>
#app {
  text-align: center;
  margin-top: 40px;
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
  margin: 0 auto;
}

.title {
  font-size: 26px;
  color: #3c90e2;
  font-weight: 600;
  letter-spacing: -1px;
  position: relative;
  display: flex;
  align-items: center;
  padding-left: 30px;
}

.title::before,
.title::after {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  border-radius: 50%;
  left: 0px;
  background-color: #3c90e2;
}

.title::before {
  width: 18px;
  height: 18px;
  background-color: #3c90e2;
}

.title::after {
  width: 18px;
  height: 18px;
  animation: pulse 1s linear infinite;
}

.message,
.price {
  color: rgba(88, 87, 87, 0.822);
  font-size: 14px;
}

.price {
  text-align: center;
}

.price a {
  color: #3c90e2;
}

.price a:hover {
  text-decoration: underline #3c90e2;
}

.submit {
  border: none;
  outline: none;
  background-color: #3c90e2;
  padding: 10px;
  border-radius: 10px;
  color: #fff;
  font-size: 16px;
  transform: 0.3s ease;
  cursor: pointer;
}

.submit:hover {
  background-color: #3c90e2;
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

.slots {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.slot {
  display: flex;
  flex-direction: column;
  margin: 0 10px;
  width: 4px;
  height: 46px;
  overflow: hidden;
  border-radius: 0.4em;
  box-shadow: 0.1em 0.1em;
  font-weight: 900;
  font-size: 18px;
  color: white;
  background: royalblue;
  font-family: inherit;
  padding: 0.1em 1.3em;
}

.number {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40px;
  font-size: 24px;
  font-weight: bold;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
}

.sticker-note {
  position: absolute;
  top: -30px;
  right: -30px;
  background: yellow;
  padding: 10px;
  border: 1px solid #ddd;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  font-weight: bold;
  transform: rotate(-10deg);
}
</style>
