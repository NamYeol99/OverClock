<template>
  <section id="hero" class="d-flex align-items-center justify-content-center">
    <div class="container2" data-aos="fade-up">
      <div class="container">
        <div class="input-form2 col-md-12 mx-auto">
          <h4 class="mb-3">관리자 페이지</h4>
          <div class="portfolio-details-slider swiper">
            <div class="swiper-wrapper align-items-center">
              <div class="icon imgsize"><img v-bind:src="img" /></div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mb-3">
              <button>총 주문 내역</button>
            </div>
            <div class="col-md-6 mb-3">
              <button>결제 내역</button>
            </div>
            <div class="col-md-6 mb-3">
              <button>
                <router-link to="/AdminMem" class="nav-link scrollto">회원 정보 조회</router-link>
              </button>
            </div>
            <div class="col-md-6 mb-3">
              <button>신고 처리</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script></script>
