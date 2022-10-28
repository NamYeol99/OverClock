<template>
	<section id="services" class="services">
      <div class="container" data-aos="fade-up">
	<div class="row">
		<div class="col-lg-4 col-md-6 align-items-stretch" data-aos="zoom-in" data-aos-delay="100"
		v-for="(list,i) in props.card" :key="(list,i)">
			<div class="icon-box">
				<br><br><br><br><br>
				<h3><a href="" style="width:292px;" >{{card[i].id}}</a></h3>
				<h3><a href="" style="width:292px;" >{{card[i].email}}</a></h3>
			</div>
		</div>
	</div>
	</div>

	</section>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  card: Object,
})

</script>

<style scoped>
.services .icon-box{
	border: 1px solid rgb(102, 102, 102);
	border-radius: 10px;
}
 a{
    text-decoration-line: none;
  }
</style>