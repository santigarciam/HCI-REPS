<template>

  <v-dialog v-model="dialog" width="900px" :key="cardID">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
    <v-btn depressed class="ma-2 mr-4" slot="activator" v-bind="attrs" v-on="on" outlined>+Anadir</v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Agregar nueva rutina</h2>
      </v-card-title>
      <v-card-text>
          <v-text-field outlined label="Nombre" v-model="nameRut"></v-text-field>
          <v-textarea outlined auto-grow label="Descripcion" v-model="detailRut" ></v-textarea>
          <v-text-field outlined label="Categoría" v-model="categoryRut"></v-text-field>


<!--        STEPPEER-->
          <div>
            <v-card class="mb-4" outlined>
              <v-card-text>
                <v-select
                    v-model="steps"
                    :items="[1,2, 3, 4, 5]"
                    label="Numero de ciclos"
                ></v-select>
              </v-card-text>
            </v-card>
            <v-stepper v-model="e1">
              <v-stepper-header>
                <v-stepper-step
                    :key="`calentamiento-step`"
                    :step="0"
                    editable
                >
                  Calentamiento
                </v-stepper-step>
                <v-divider
                    v-if="n !== steps"
                    :key="n"
                ></v-divider>
                <template v-for="n in steps">
                  <v-stepper-step
                      :key="`${n}-step`"

                      :step="n"
                      editable
                  >
                   {{ n }}º Ciclo
                  </v-stepper-step>

                  <v-divider
                      v-if="n !== steps"
                      :key="n"
                  ></v-divider>
                </template>
                <v-divider
                    v-if="n !== steps"
                    :key="n"
                ></v-divider>
                <v-stepper-step
                    :key="`enfriemiento-step`"
                    :step="steps+1"
                    editable
                >
                  Enfriamiento
                </v-stepper-step>
              </v-stepper-header>

<!--             DATA STEEPPER CALENTAMIENTO -->
              <v-stepper-items>
                  <v-stepper-content
                      :key="`calentamiento-content`"
                      :step="0"
                  >
                    <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="200px"
                    >
                      <v-list two-line  style="max-height:250px"

                              class="overflow-y-auto">

                        <v-list-item-group
                            v-model="selected[0]"
                            active-class="grey--text"
                            multiple
                        >
                          <template v-for="(excersise, index) in ejercicios ">
                            <v-list-item :key="excersise.id">
                              <template v-slot:default="{ active }">
                                <v-list-item-content>
                                  <v-list-item-title v-text="excersise.name"></v-list-item-title>

                                </v-list-item-content>

                                <v-list-item-action>
<!--                                  <v-list-item-action-text v-text="excersise.action"></v-list-item-action-text>-->

                                  <v-icon
                                      v-if="active"
                                      color="grey lighten-1"
                                  >
                                    mdi-check
                                  </v-icon>

                                </v-list-item-action>
                              </template>
                            </v-list-item>

                            <v-divider
                                v-if="index < excersise.length - 1"
                                :key="index"
                            ></v-divider>
                          </template>
                        </v-list-item-group>

                      </v-list>


                    </v-card>
                  </v-stepper-content>
                <!--             DATA STEEPPER CALENTAMIENTO FIN -->


                <!--             DATA STEEPPER ENFRIAMIENTO -->
                <v-stepper-content
                    :key="`enfriamiento-content`"
                    :step="steps+1"
                >
                  <v-card
                      class="mb-12"
                      color="grey lighten-1"
                      height="200px"
                  >
                    <v-list two-line  style="max-height:250px"

                            class="overflow-y-auto">

                      <v-list-item-group
                          v-model="selected[steps+1]"
                          active-class="grey--text"
                          multiple
                      >
                        <template v-for="(excersise, index) in ejercicios ">
                          <v-list-item :key="excersise.id">
                            <template v-slot:default="{ active }">
                              <v-list-item-content>
                                <v-list-item-title v-text="excersise.name"></v-list-item-title>

                              </v-list-item-content>

                              <v-list-item-action>
                                <!--                                  <v-list-item-action-text v-text="excersise.action"></v-list-item-action-text>-->

                                <v-icon
                                    v-if="active"
                                    color="grey lighten-1"
                                >
                                  mdi-check
                                </v-icon>

                              </v-list-item-action>
                            </template>
                          </v-list-item>

                          <v-divider
                              v-if="index < excersise.length - 1"
                              :key="index"
                          ></v-divider>
                        </template>
                      </v-list-item-group>

                    </v-list>

                  </v-card>

                </v-stepper-content>

                <!--             DATA STEEPPER ENFRIAMIENTO FIN -->

                <!--             DATA STEEPPER CICLOS -->
                <v-stepper-content
                    v-for="n in steps"
                    :key="`${n}-content`"
                    :step="n"
                >
                  <v-card
                      class="mb-12"
                      color="grey lighten-1"
                      height="200px"
                  >
                    <v-list two-line  style="max-height:250px"

                            class="overflow-y-auto">

                      <v-list-item-group
                          v-model="selected[n]"
                          active-class="grey--text"
                          multiple
                      >
                        <template v-for="(excersise, index) in ejercicios ">
                          <v-list-item :key="excersise.id">
                            <template v-slot:default="{ active }">
                              <v-list-item-content>
                                <v-list-item-title v-text="excersise.name"></v-list-item-title>

                              </v-list-item-content>

                              <v-list-item-action>
                                <v-list-item-action-text v-text="excersise.action"></v-list-item-action-text>

                                <v-icon
                                    v-if="active"
                                    color="grey lighten-1"
                                >
                                  mdi-check
                                </v-icon>

                              </v-list-item-action>
                            </template>
                          </v-list-item>

                          <v-divider
                              v-if="index < excersise.length - 1"
                              :key="index"
                          ></v-divider>
                        </template>
                      </v-list-item-group>

                    </v-list>
                  </v-card>

                </v-stepper-content>
                <!--             DATA STEEPPER CICLOS FIN -->
              </v-stepper-items>
            </v-stepper>
          </div>

        <v-col></v-col>
                  <v-col>
                    <v-row>
                      <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                      <v-btn flat dark class="red mx-0" v-on:click="cancelActionNewRut">Cancelar</v-btn>
                      <v-btn flat class="success mx-10" v-on:click="addNewRoutine">Guardar</v-btn>
<!--                      <v-btn flat class="success mx-10" v-on:click="pqAnda">Guardar</v-btn>-->

                    </v-row>
                  </v-col>
              </v-card-text>
            </v-card>
  </v-dialog>
</template>

<script>

// import rutineCard from "./rutineCard.vue";
  import  { routineApi } from "@/API_EJS/js/routines"
import  { categoryApi} from "../API_EJS/js/category";
import {cycleApi} from "../API_EJS/js/cycles";
import  {cycleExercisesApi} from "../API_EJS/js/cycleExercises";
//import state from "/"

export default {
  name: "nuevaRutina",
  components: {},
  data(){
    return{
      nameRut:'',
      selected:{},
      detailRut:'',
      categoryRut:'',
      e1: 0,
      steps: 1,
      durRut:'',
      ciclos: ['Entrada en calor', 'Entrenamiento', 'Enfriamiento'],
    }
  },
  watch: {
    steps (val) {
      if (this.e1 > val) {
        this.e1 = val
      }
    },
  },
  methods :{
    // generarRutinaNueva(tituloRut, autorRut, descripcionRut, durRut, rating)
    addNewRoutine: async function(){
      var catID = -1;
      var respCat;
      try {
         respCat = await categoryApi.add({name: this.categoryRut, detail: "N/A"});
        catID = respCat.id;
      }catch (error){
           var auxCats = await categoryApi.getAll(null);
           if (auxCats.totalCount > 0) {
                var arr = auxCats.content;
             for (var j = 0; j < arr.length; j++) {
               if (arr[j].name === this.categoryRut) {
                 catID = arr[j].id;
               }
             }
             if(catID === -1){
               console.log("ERROR CATEGORIAS 1");
               return;
             }
           } else {
             console.log("ERROR CATEGORIAS 2" );
             return;
           }
      }
      // Lo de arriba es para agregar/usar una categoria dependiendo si esta o no creada.

          const respRut =  await routineApi.add({name:this.nameRut,detail:this.detailRut,isPublic:true,difficulty:"rookie",category:{ id: catID},metadata:null},null);
      console.log("Resprut : ");
          console.log(respRut);
          if(respRut.id) {
            for (var i = 0; i < this.steps + 2; i++) {
              let t = 1;
              switch (i) {
                case 0:
                  // eslint-disable-next-line no-case-declarations
                  const respCal = await cycleApi.add(respRut.id, {
                    name: "Calentamiento",
                    detail: "N/A",
                    type: "warmup",
                    order: 1,
                    repetitions: 1,
                    metadata: null
                  }, null);
                  if (respCal.id) {
                    for (const k of this.selected[i]) {
                      await cycleExercisesApi.add(respCal.id, this.ejercicios[k].id, {
                        order: t++,
                        duration: 1,
                        repetitions: 1
                      }, null);
                    }
                    console.log(await cycleApi.get(respRut.id,respCal,null));
                  } else {
                    console.log("Error calentamiento");
                    return;
                  }
                  console.log("Creo calentamiento");
                  break;
                case (this.steps + 1):
                  /////
                  //eslint-disable-next-line no-case-declarations
                  const respEnfri = await cycleApi.add(respRut.id, {
                    name: "Enfriamiento",
                    detail: "N/A",
                    type: "cooldown",
                    order: i + 1,
                    repetitions: 1,
                    metadata: null
                  }, null);
                  if (respEnfri.id) {
                    for (const k of this.selected[i]) {
                      await cycleExercisesApi.add(respEnfri.id, this.ejercicios[k].id, {
                        order: t++,
                        duration: 1,
                        repetitions: 1
                      }, null);
                    }
                  } else {
                    console.log("ERROR ENFRI"); //ERROR
                  }
                  break;
                default:
                  //eslint-disable-next-line no-case-declarations
                  const respCiclo = await cycleApi.add(respRut.id, {
                    name: "Ciclo " + i,
                    detail: "N/A",
                    type: "exercise",
                    order: i + 1,
                    repetitions: 1,
                    metadata: null
                  }, null);
                  if (respCiclo.id) {
                    for (const k of this.selected[i]) {
                      await cycleExercisesApi.add(respCiclo.id, this.ejercicios[k].id, {
                        order: t++,
                        duration: 1,
                        repetitions: 1
                      }, null);
                    }
                    console.log(await cycleApi.get(respRut.id,respCiclo,null));
                  } else {
                    console.log("Error ciclo"); //ERROR
                  }
                  break;
              }
            }
          }else{
            console.log("No se pudo crear la rutina"); //ERROR
          }
          // Lo de arriba es toda la logica de crear una rutina (rutina, ciclos, ejercicios)


      const rutinaAux = await routineApi.get(respRut.id,null);
      console.log(rutinaAux);
      const ciclosAux = await cycleApi.getAll(respRut.id, null);
      console.log(ciclosAux);
      for (const h of ciclosAux.content) {
        console.log(h.id);
        var ejerCiclo = await cycleExercisesApi.getAll(h.id, null);
        console.log(ejerCiclo);
      }

    this.cancelActionNewRut();
    },
    cancelActionNewRut: function (){
      console.log("CANCEL");
      this.dialog = false;
      this.nameRut='';
      this.e1= 0;
      this.steps= 1;
      this.selected={};
      this.detailRut='';
      this.categoryRut='';
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    },
  computed: {
    ejercicios(){
      return this.$store.state.listaEjercicios;
    },
    cardID(){
      return this.$store.state.cardID;
    }
  },
  mounted() {
    this.$store.dispatch("getExercises");
  }
}
</script>

<style scoped>

</style>