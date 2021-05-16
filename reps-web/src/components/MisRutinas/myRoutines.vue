<template>
  <div class="text-center">
    <v-dialog
        width="900px"
        v-for="(rutina, index) in rutinas"
        :key="[index, rutina.id]"
        scrollable
    >
      <template v-slot:activator="{ on, attrs }">
        <v-container class ="container_v_card pb-4">
          <v-card v-bind="attrs" v-on="on"  @click.stop="funcionAUX(rutina.id,rutina)"  :data="modalData" scrollable>
            <v-col>
              <v-row>
                <v-card-title prepend-icon="mdi-counter" class="mb-0 pb-0">{{ rutina.name }}<v-icon v-if="! rutina.isPublic" color="black" class="ml-2">mdi-lock</v-icon></v-card-title>
                <v-spacer></v-spacer>
                <!-- EDITAR RUTINAA-->
                <edit-routine :rutina="rutina"></edit-routine>
                <!--                  EDITAR RUTINA FIN-->
                <share-routine v-bind:rutina="rutina"></share-routine>
                <delete-routine v-bind:rutina="rutina"></delete-routine>
             </v-row>

              <!--INFORMACION RUTINE CARD-->
              <v-row class="text-left">
                <v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripción: </v-card-subtitle>
                <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
              </v-row>
            </v-col>

          </v-card>
        </v-container>

      </template>
    <detailed-routine v-bind:rutina="rutina" v-bind:show-username="false"></detailed-routine>

    </v-dialog>
  </div>
</template>




<script>
import NuevaRutina from "@/components/MisRutinas/nuevaRutina";
import  EditRutina from "@/components/MisRutinas/editRoutine"
import DetailedRoutine from "../detailedRoutine";
import EditRoutine from "./editRoutine";
import DeleteRoutine from "./deleteRoutine";
import ShareRoutine from "../shareRoutine";


export default {
  components: {ShareRoutine, DeleteRoutine, EditRoutine, DetailedRoutine},
  componets: {NuevaRutina, EditRutina},
  data() {
    return {

      dialog:{id:0,on:false},
      selected:[],
      modalVisible:null,
      dialogEdit:false,
      e1: 0,
      steps: 1,
      rutAux:{id:0,name:'',detail:'',category:{name:'',id:0}},
      titleRut:'',
      cyclesAux:[],
      excercisesOfCycleAUX:[],
    }
  },
  methods: {
    data: function ()  {
      return {
        modalData: null,
      }
    },


    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    //////////////////////////////////////////////////////////////////////////////////
    funcionAUX: async function (id,item){
      this.data.modalData = item;
      this.data.modalVisible = true;
      // ver de hacer el dispatch aca

      await this.$store.dispatch("getCyclesOfID", id);

    },


    /////////////////////////////////////////////////////////////////////////////////
  },
  watch: {
    steps (val) {
      if (this.e1 > val) {
        this.e1 = val
      }
    },
  },

  computed: {
    rutinas(){
      return this.$store.state.listaRutinas;
    },
    cardID(){
      return this.$store.state.cardID;
    },
    ejercicios(){
      return this.$store.state.listaEjercicios;
    },
    cyclesOfRutine(){
      console.log(this.$store.state.cyclesOfRutine);
      return this.$store.state.cyclesOfRutine;
    },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    }, ////// DESCOMENTAR CUANDO EL API FUNCIONE
    ejsCycleAux(){
      return this.$store.state.ejsCycleAux;
    }
  },

  async mounted() {
    await this.$store.dispatch("getUserRoutines");
  },
}
</script>