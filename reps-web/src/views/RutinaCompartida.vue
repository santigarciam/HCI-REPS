<template>
  <div class=" bg v-application--wrap inspire" >
    <v-dialog persistent width="800px" v-model="dialog">

      <DetailedRoutine v-bind:show-username="true" v-bind:username="sharedRut.user.username" v-bind:rutina="sharedRut"></DetailedRoutine>
    </v-dialog>

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
      dialog : true,
    }
  },
  async beforeCreate() {
    this.currentURL = router.currentRoute.fullPath;
    await this.$store.dispatch("getRoutineByID", router.currentRoute.fullPath);
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
  background-image: url("../assets/fondoPrueba.jpg");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
.botonVolver{

}
#inspire img {
  height: 100%
}
.v-application--wrap {
  height: 100vh !important;
}

</style>