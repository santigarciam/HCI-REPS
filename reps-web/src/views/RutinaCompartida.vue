<template>
  <div class=" bg v-application--wrap inspire" >
    <v-container fluid class=" pa-0 ma-0">
      <v-row>
        <v-col>
          <v-row justify="center" align="end" style="height: 550px">

            <v-card scrolleable max-height="800px" width="900px"   justify="center">
              <DetailedRoutine v-bind:rutina="sharedRut"></DetailedRoutine>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
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
    this.currentURL = router.currentRoute.fullPath;
    console.log( this.currentURL);
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
  background-image: url("../assets/principal2.png");
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