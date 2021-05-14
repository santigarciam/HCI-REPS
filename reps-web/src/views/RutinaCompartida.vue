<template>
  <div class="bg">
      <DetailedRoutine v-bind:rutina="sharedRut"></DetailedRoutine>
  </div>
</template>

<script>
import { router } from "../main";
import DetailedRoutine from "../components/detailedRoutine";

export default {
  components: {DetailedRoutine},
  name: "RutinaCompartida",
  data(){
    return{
      currentURL: "",
    }
  },
  async beforeCreate() {
    this.currentURL = router.currentRoute;
    await this.$store.dispatch("getRoutineByID", this.currentURL);
    //while (this.$store.state.rutinaCompartida === undefined);
    console.log(this.sharedRut);
    console.log("rutinaCompartida: listo beforeCreate");
  },
  computed:{
    sharedRut(){
      return this.$store.state.rutinaCompartida;
    }
  }
}
</script>

<style scoped>
a { text-decoration: none; }

.bg {
  background-image: url("../assets/principal2.png");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
.botonVolver{

}
</style>