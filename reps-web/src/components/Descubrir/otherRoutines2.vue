<template>
  <div class="text-center">
    <v-dialog
        width="900px"
        v-for="(rutina,index) in rutinas"
        :key="[index, rutina.id]"
        scrollable
    >
      <!--        v-for="rutina in data().rutinas" :key="rutina.tituloRut"  UNA LINEA MAS ARRIBA -->

      <template v-slot:activator="{ on, attrs }">
        <v-container class ="container_v_card pb-4">
          <v-card v-bind="attrs" hover v-on="on"  @click.stop="funcionAUX(rutina.id,rutina)"  :data="modalData" scrollable>
            <v-col>
              <v-row>
                <v-card-title class="mb-0 pb-0">{{ rutina.name }}
                  <v-rating
                      v-model="rutina.averageRating"
                      half-increments
                      background-color="grey"
                      color="#00B2EB"
                      medium
                      class="ml-2"
                  ></v-rating></v-card-title>
                <v-spacer></v-spacer>

                  <share-routine v-bind:rutina="rutina"></share-routine>

                  <fav-routine v-bind:rutina="rutina" ></fav-routine>

              </v-row>

              <!--INFORMACION RUTINE CARD-->
              <v-row class="text-left">
                <v-icon small class="align-center  ml-3" color="blue">mdi-account</v-icon>
                <v-card-subtitle class="blue--text ml-0 pl-1 pt-0 pb-0 font-weight-bold" v-model="autorRut">{{ rutina.user.username }} </v-card-subtitle>
              </v-row>
              <v-row><v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripción: </v-card-subtitle>
                <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
              </v-row>
            </v-col>

          </v-card>
        </v-container>

      </template>

      <detailed-routine v-bind:show-username="true" v-bind:username="rutina.user.username" v-bind:rutina="rutina"></detailed-routine>

    </v-dialog>
  </div>
</template>




<script>

import DetailedRoutine from "../detailedRoutine";
import FavRoutine from "../favRoutine";
import ShareRoutine from "../shareRoutine";

export default {
  components: {ShareRoutine, FavRoutine, DetailedRoutine},
  data() {
    return {
      snackbar: false,
      dialog:{id:0,on:false},
      selected:[],
      modalVisible:null,
      dialogEdit:false,
      modalData: null,
    }
  },
  methods: {
    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    //////////////////////////////////////////////////////////////////////////////////
    funcionAUX: async function (id,item){
      this.$data.modalData = item;
      this.$data.modalVisible = true;
      // ver de hacer el dispatch aca

      await this.$store.dispatch("getCyclesOfID", id);

    },
     /////////////////////////////////////////////////////////////////////////////////
  },

  computed: {
    rutinas(){
      return this.$store.state.otrasRutinas;
    },
    cardID(){
      return this.$store.state.cardID;
    }
  },
  created() {
    console.log("created del other routines")
  },
  updated() {
    console.log("updated del other")
  },
  beforeMount() {
    console.log("before mount del other routines")
  },
  mounted() {
    //this.$store.dispatch("getRoutines", "");
    console.log("sera aca")
    this.$store.dispatch("getFavourites", "");
   console.log("1")
  },
}
</script>