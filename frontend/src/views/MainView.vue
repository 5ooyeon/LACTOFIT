<template>
  <div class="main-scroll">
    <main>
      <section class="home section">
        <video src="https://kr.object.ncloudstorage.com/thebody/hit35_main_width_240326.mp4" autoplay loop muted></video>
        <div class="home__content">
          <p class="home_title">
            운동이 쉬워진다.<br />
            樂土必으로<br />
            운동 고민 끝
          </p>

          <button class="animate">
            <span class="circle" aria-hidden="true">
              <span class="icon arrow"></span>
            </span>
            <RouterLink class="button-text" :to="{name: 'RoutineInfoComponent'}" style="text-decoration: none;">나의 루틴 등록하기</RouterLink>
          </button>
        </div>
      </section>

      <!--==================== routine ====================-->
      <section class="routine section" id="routine">
        <div class="routine__container container">
          <div class="routine__content">
            <div class="routine__image">
              <img src="./../assets/img/LACTOFIT_main_1.jpg" alt="new image" />
            </div>
            <div class="routine__info">
              <h2 class="routine__title">원하는 운동을 찾아보세요.</h2>
              <p class="routine__description">
                운동명으로 검색하여 원하는 운동에 대한 정보를 확인하고, 맞춤형 루틴을 만들어보세요. 다양한 운동 정보를 통해 더욱 효과적인 운동을 경험할 수 있습니다.
              </p>
              <button class="goto">
                <RouterLink :to="{name: 'ExerciseView'}" style="text-decoration: none; color: black;">운동 검색하기</RouterLink>
              </button>
            </div>
          </div>
        </div>
      </section>

      <!--==================== youtube ====================-->
      <section class="youtube section" id="youtube">
        <div class="youtube__container container">
          <div class="youtube__content">
            <div class="youtube__info">
              <h2 class="youtube__title">
                운동 시 자세 잡는 것이 어렵다면?<br />
                운동 별 영상을 제공해드려요.
              </h2>
              <p class="youtube__description">
                운동명으로 검색하여 원하는 운동에 대한 자세한 설명과 올바른 자세를 확인해보세요. 각 운동의 단계별 수행 방법, 주의사항, 그리고 효과를 상세히 알려드립니다. 또한, 관련 유튜브 영상을 통해 보다 쉽게 운동을 따라할 수 있습니다. 체계적인 운동 계획을 세워 목표를 달성하는 데 도움을 드립니다.
              </p>
              <button class="goto">
                <RouterLink :to="{name:'YoutubeMainComponent'}" style="text-decoration: none; color: black;">영상 찾아보기</RouterLink>
              </button>
            </div>
            <div class="youtube__image">
              <img src="./../assets/img/LACTOFIT_main_2.png" alt="new image" />
            </div>
          </div>
        </div>
      </section>

      <!--==================== social ====================-->
      <section class="social section" id="social">
        <h2 class="social__title">
          “오늘만 운동 쉴까"<br />
          함께 이겨내요.
        </h2>
        <p class="social__description">
          LACTOFIT 커뮤니티에서 소통하고, 공유하고, 응원하세요.
        </p>

        <div class="social__container container grid">
          <div class="social__card" v-for="board in boardList.slice(0, 8)" :key="board.id">
            <img :src="board.board.boardImgUrl" alt="social image" class="social__img" />
          </div>
        </div>

        <div class="social__button-container container">
          <button class="goto">
            <RouterLink :to="{name: 'SocialMainComponent'}" style="text-decoration: none;">#오운완 인증하기</RouterLink>
          </button>
        </div>
      </section>
    </main>

    <!-- Scroll to Top Button -->
    <button id="scrollToTopBtn" class="scroll-top-button">
      <div class="top-button-box">
        <span class="top-button-elem">
          <svg viewBox="0 0 46 40" xmlns="http://www.w3.org/2000/svg">
            <path d="M23 5l-17 17c-1.1 1.1-1.1 3.1 0 4.2s3.1 1.1 4.2 0L23 14.2l13.8 13.8c1.1 1.1 3.1 1.1 4.2 0s1.1-3.1 0-4.2L23 5z"></path>
          </svg>
        </span>
        <span class="top-button-elem">
          <svg viewBox="0 0 46 40">
            <path d="M23 5l-17 17c-1.1 1.1-1.1 3.1 0 4.2s3.1 1.1 4.2 0L23 14.2l13.8 13.8c1.1 1.1 3.1 1.1 4.2 0s1.1-3.1 0-4.2L23 5z"></path>
        </svg>
        </span>
      </div>
    </button>

    <footer class="footer">
      <div class="container">
        <div class="footer__content">
          <div class="footer__left">
            <h2 class="footer__title">SSAFIT LACTOFIT</h2>
            <p>Lee Dongyeop, Cho Sooyeon</p>
          </div>
          <div class="footer__right">
            <div class="footer__info">
              <h3 class="footer__info-title">Location</h3>
              <p>06220<br />서울 강남구 테헤란로 212 205호 왼쪽 뒤에서 두번째</p>
            </div>
            <div class="footer__info">
              <h3 class="footer__info-title">Contact</h3>
              <p><a href="mailto:ssafitlactofit@gmail.com">ssafitlactofit@gmail.com</a></p>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

const boardList = ref([]);
const getPopularBoardList = () => {
  axios.get('http://localhost:8080/boards/likes')
    .then((response) => {
      boardList.value = response.data.slice(0, 8); // Limit to top 8 posts
    });
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  });
};

const handleScroll = () => {
  const scrollToTopBtn = document.getElementById('scrollToTopBtn');
  if (window.scrollY > 200) {
    scrollToTopBtn.style.display = 'block';
  } else {
    scrollToTopBtn.style.display = 'none';
  }
};

const observer = new IntersectionObserver(entries => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.add('animated');
    } else {
      entry.target.classList.remove('animated');
    }
  });
});

onMounted(() => {
  getPopularBoardList();
  const scrollToTopBtn = document.getElementById('scrollToTopBtn');
  scrollToTopBtn.addEventListener('click', scrollToTop);
  window.addEventListener('scroll', handleScroll);

  document.querySelectorAll('.section').forEach(section => {
    observer.observe(section);
  });
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
  observer.disconnect();
});
</script>

<style scoped>
.home {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  margin-top: 0;
  padding-top: 0;
}

video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
}

.home::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 0;
}

.home_title {
  font-weight: 500;
}

.home__content {
  position: absolute;
  z-index: 1;
  top: 50%;
  left: 30%;
  transform: translate(-50%, -50%);
  color: white;
  font-weight: 900;
  font-size: 60px;
}

.routine__container,
.youtube__container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.routine__content,
.youtube__content {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.routine__image,
.youtube__image {
  flex: 1;
  padding-right: 2rem;
}

.youtube__image {
  justify-content: flex-end;
  padding-right: 0;
}

.routine__image img,
.youtube__image img {
  width: 35rem;
  height: auto;
  object-fit: cover;
}

.routine__info,
.youtube__info {
  flex: 1;
  padding-left: 2rem;
}

.routine__title,
.youtube__title {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
}

.routine__description,
.youtube__description {
  font-size: 1rem;
  color: #666;
  margin-bottom: 1.5rem;
}

.social__title {
  font-size: 2rem;
  font-weight: 700;
  text-align: center;
  margin-bottom: 2rem;
}

.social__description {
  text-align: center;
}

.social__container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  margin-bottom: 2rem;
}

.social__card {
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.social__card:hover {
  transform: scale(1.05);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.social__img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.footer {
  background-color: #f8f8f8;
  padding: 2rem 0;
}

.footer__content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.footer__left {
  flex: 1;
}

.footer__title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.footer__right {
  display: flex;
  gap: 2rem;
  flex: 1;
  justify-content: flex-end;
}

.footer__info {
  flex: 1;
}

.footer__info-title {
  font-weight: 600;
  margin-bottom: 0.5rem;
}

@media (max-width: 768px) {
  .routine__content,
  .youtube__content {
    flex-direction: column;
  }

  .routine__image,
  .routine__info,
  .youtube__image,
  .youtube__info {
    padding: 0;
  }

  .social__container {
    grid-template-columns: repeat(2, 1fr);
  }

  .footer__content {
    flex-direction: column;
    align-items: flex-start;
  }

  .footer__right {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
    margin-top: 1rem;
  }

  .footer__info {
    flex: none;
  }
}

.animate {
  position: relative !important;
  display: inline-block !important;
  cursor: pointer !important;
  outline: none !important;
  border: 0 !important;
  vertical-align: middle !important;
  text-decoration: none !important;
  background: transparent !important;
  padding: 0 !important;
  font-size: inherit !important;
  font-family: inherit !important;
}

button.animate {
  width: 12rem;
  height: auto;
}

button.animate .circle {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: relative;
  display: block;
  margin: 0;
  width: 3rem;
  height: 3rem;
  background: #3c90e2;
  border-radius: 1.625rem;
}

button.animate .circle .icon {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto;
  background: #fff;
}

button.animate .circle .icon.arrow {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  left: 0.625rem;
  width: 1.125rem;
  height: 0.125rem;
  background: none;
}

button.animate .circle .icon.arrow::before {
  position: absolute;
  content: "";
  top: -0.29rem;
  right: 0.0625rem;
  width: 0.625rem;
  height: 0.625rem;
  border-top: 0.125rem solid #fff;
  border-right: 0.125rem solid #fff;
  transform: rotate(45deg);
}

button.animate .button-text {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1) !important;
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  bottom: 0 !important;
  padding: 0.75rem 0 !important;
  margin: 0 0 0 1.85rem !important;
  color: white;
  font-weight: 700 !important;
  line-height: 1.6 !important;
  text-align: center !important;
  text-transform: uppercase !important;
  font-size: 15px !important;
}

.animate:hover .circle {
  width: 100%;
}

.animate:hover .circle .icon.arrow {
  background: #fff;
  transform: translate(1rem, 0);
}

.animate:hover .button-text {
  color: #fff !important;
}

.goto {
  padding: 15px 25px;
  border: unset;
  border-radius: 15px;
  color: #212121;
  z-index: 1;
  background: #e8e8e8;
  position: relative;
  font-weight: 1000;
  font-size: 17px;
  box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  transition: all 250ms;
  overflow: hidden;
}

.goto::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 0;
  border-radius: 15px;
  background-color: #3297CE;
  color: #e8e8e8;
  z-index: -1;
  box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  transition: all 250ms;
}

.goto:hover {
  color: #e8e8e8 !important;
}

.goto:hover::before {
  width: 100%;
}

.scroll-top-button {
  display: block;
  position: fixed;
  bottom: 20px;
  right: 30px;
  width: 56px;
  height: 56px;
  margin: 0;
  overflow: hidden;
  outline: none;
  background-color: transparent;
  cursor: pointer;
  border: 0;
  z-index: 99;
}

.scroll-top-button:before,
.scroll-top-button:after {
  content: "";
  position: absolute;
  border-radius: 50%;
  inset: 7px;
}

.scroll-top-button:before {
  border: 4px solid #ff0c86;
  transition: opacity 0.4s cubic-bezier(0.77, 0, 0.175, 1) 80ms,
    transform 0.5s cubic-bezier(0.455, 0.03, 0.515, 0.955) 80ms;
}

.scroll-top-button:after {
  border: 4px solid #96daf0;
  transform: scale(1.3);
  transition: opacity 0.4s cubic-bezier(0.165, 0.84, 0.44, 1),
    transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94);
  opacity: 0;
}

.scroll-top-button:hover:before,
.scroll-top-button:focus:before {
  opacity: 0;
  transform: scale(0.7);
  transition: opacity 0.4s cubic-bezier(0.165, 0.84, 0.44, 1),
    transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.scroll-top-button:hover:after,
.scroll-top-button:focus:after {
  opacity: 1;
  transform: scale(1);
  transition: opacity 0.4s cubic-bezier(0.77, 0, 0.175, 1) 80ms,
    transform 0.5s cubic-bezier(0.455, 0.03, 0.515, 0.955) 80ms;
}

.top-button-box {
  display: flex;
  position: absolute;
  top: 0;
  left: 0;
}

.top-button-elem {
  display: block;
  width: 20px;
  height: 20px;
  margin: 17px 18px 0 18px;
  fill: #ff0c86;
}

.scroll-top-button:hover .top-button-box,
.scroll-top-button:focus .top-button-box {
  transition: 0.4s;
  transform: translateY(-56px);
}

/* Section animation */
.section {
  opacity: 0;
  transform: translateY(20px);
  transition: opacity 0.6s ease, transform 0.6s ease;
}

.section.animated {
  opacity: 1;
  transform: translateY(0);
}
</style>
