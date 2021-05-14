<template>

  <v-dialog persistent v-model="dialog" width="900px" :key="cardID">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
    <v-btn depressed class="ma-2 mr-4" slot="activator" v-bind="attrs" v-on="on" outlined>+Anadir</v-btn>
    </template>

    <v-card flat>
      <v-card-title class="white--text primary">
        AGREGAR NUEVA RUTINA
      </v-card-title>
      <v-card-text>
        <v-col></v-col>
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
                        elevation="0"
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

                      <!--            Si no tiene ejercicios cargados          -->

                      <v-card
                          v-if="ejercicios.length === 0"
                          class="mb-12"
                          color="white lighten-1"
                          height="200px"
                          elevation="0"
                      >
                        <v-col></v-col>
                        <v-col></v-col>
                        <v-row
                            align="center"
                            justify="center"
                        >
                          <v-col
                              class="text-center"
                              cols="24"
                          >
                            <h1 class="display-1 font-weight-thin mb-4">
                              <span>... Debes </span>
                              <span class="blue--text boldText">crear ejercicios</span>
                              <span> antes de crear rutinas ...</span>
                            </h1>
                            <h4 class="subheading">
                              Aqui apareceran tus ejercicios creados
                            </h4>
                          </v-col>
                        </v-row>
                      </v-card>

                      <!--                      -->


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
                      elevation="0"
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
                                <v-row>
                                  <v-col>
                                <v-list-item-title v-text="excersise.name"></v-list-item-title>
                                  </v-col>
                                </v-row>
                              </v-list-item-content>

                              <v-list-item-action>
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

                    <!--            Si no tiene ejercicios cargados          -->

                    <v-card
                        v-if="ejercicios.length === 0"
                        class="mb-12"
                        color="white lighten-1"
                        height="200px"
                        elevation="0"
                    >
                      <v-col></v-col>
                      <v-col></v-col>
                      <v-row
                          align="center"
                          justify="center"
                      >
                        <v-col
                            class="text-center"
                            cols="24"
                        >
                          <h1 class="display-1 font-weight-thin mb-4">
                            <span>... Debes </span>
                            <span class="blue--text boldText">crear ejercicios</span>
                            <span> antes de crear rutinas ...</span>
                          </h1>
                          <h4 class="subheading">
                            Aqui apareceran tus ejercicios creados
                          </h4>
                        </v-col>
                      </v-row>
                    </v-card>

                    <!--                      -->

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
                      elevation="0"
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

                    <!--            Si no tiene ejercicios cargados          -->

                    <v-card
                        v-if="ejercicios.length === 0"
                        class="mb-12"
                        color="white lighten-1"
                        height="200px"
                        elevation="0"
                    >
                      <v-col></v-col>
                      <v-col></v-col>
                      <v-row
                          align="center"
                          justify="center"
                      >
                        <v-col
                            class="text-center"
                            cols="24"
                        >
                          <h1 class="display-1 font-weight-thin mb-4">
                            <span>... Debes </span>
                            <span class="blue--text boldText">crear ejercicios</span>
                            <span> antes de crear rutinas ...</span>
                          </h1>
                          <h4 class="subheading">
                            Aqui apareceran tus ejercicios creados
                          </h4>
                        </v-col>
                      </v-row>
                    </v-card>

                    <!--                      -->

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
                      <v-btn color="grey lighten-1 white--text" class="mx-0" v-on:click="cancelActionNewRut">Cancelar</v-btn>
<!--                      <v-btn flat class="primary mx-10" v-on:click="addNewRoutine">Guardar</v-btn>&ndash;&gt;-->
                      <v-dialog persistent width="900px" :key="cardID">
                      <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
                        <v-btn class="primary mx-10 white--text"  slot="activator" v-bind="attrs" v-on="on" @click="loadNextStepNewRut" outlined>Siguiente</v-btn>
                      </template>

                      <v-card>
                        <v-card-title>Repeticiones y Descansos</v-card-title>
                        <h4 class="pt-6 pl-6 mb-4">Ciclos:</h4>
                        <v-card-text>
                        <v-expansion-panels class="mb-2" v-for="i in steps + 2"  :key="i">
                          <v-expansion-panel>
                            <v-row>
                              <v-col>
                            <v-expansion-panel-header v-if="i===1">Calentamiento </v-expansion-panel-header>
                            <v-expansion-panel-header v-else-if="i=== steps + 2">Enfriamiento</v-expansion-panel-header>
                            <v-expansion-panel-header v-else> Ciclo {{i -1}}</v-expansion-panel-header>
                              </v-col>
                            <v-col align="center">

                              <v-text-field
                                  prepend-icon="mdi-counter"
                                  v-model="repsCiclo[i -1]"
                                  class="mt-0 pt-0"
                                  hide-details
                                  single-line
                                  min = "1"
                                  type="number"
                                  style="width: 80px"
                              ></v-text-field>
                            </v-col>
                            </v-row>
                            <v-expansion-panel-content>
                              <template v-for="(ejs,j) in ejsCycleAux[i-1]">
                                <v-card small class="mt-1 mb-1" :key="ejs.id">

                                  <v-row>
                                    <v-col>
                                      <v-card-text>{{ejs.ej.name}}</v-card-text>
                                    </v-col>
                                    <v-spacer></v-spacer>

<!--                                    <v-col><v-subheader>Orden</v-subheader></v-col>-->
                                    <v-col>
                                      <v-text-field
                                          prepend-icon="mdi-order-numeric-ascending"
                                          v-model="ejsCycleAux[i-1][j].orden"
                                          class="mt-0 pt-0"
                                          hide-details
                                          single-line
                                          min = "1"
                                          type="number"
                                          style="width: 60px"
                                      ></v-text-field>
                                    </v-col>
                                    <v-col>
                                      <v-text-field
                                          prepend-icon="mdi-counter"
                                          v-model="ejsCycleAux[i-1][j].reps"
                                          class="mt-0 pt-0"
                                          hide-details
                                          single-line
                                          min = "1"
                                          type="number"
                                          style="width: 60px"
                                      ></v-text-field>
                                    </v-col>
                                    <v-col>
                                      <v-text-field
                                          prepend-icon="mdi-clock-outline"
                                          v-model="ejsCycleAux[i-1][j].desc"
                                          class="mt-0 pt-0"
                                          hide-details
                                          min = "1"
                                          single-line
                                          type="number"
                                          style="width: 60px"
                                      ></v-text-field>
                                    </v-col>
                                  </v-row>
                                </v-card>
                                <!--                  </template>-->
                                <!--                </v-card>-->
                              </template>
                            </v-expansion-panel-content>
                          </v-expansion-panel>
                        </v-expansion-panels>
                        <v-col></v-col>
                        <v-col>
                          <v-row>
                            <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                            <v-btn color="grey lighten-1 white--text" class="mx-0" v-on:click="cancelActionNewRut">Cancelar</v-btn>
                            <v-btn :loading="loading" class="primary mx-10 white--text" v-on:click="addNewRoutine">Finalizar</v-btn>
                          </v-row>
                        </v-col>
                        <v-col></v-col>
                        </v-card-text>
                      </v-card>

                      </v-dialog>


                    </v-row>
                  </v-col>
              </v-card-text>
            </v-card>
  </v-dialog>




<!--  </v-dialog>-->


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
      loading: false,
      nameRut:'',
      selected:{},
      detailRut:'',
      categoryRut:'',
      e1: 0,
      steps: 1,
      durRut:'',
      ciclos: ['Entrada en calor', 'Entrenamiento', 'Enfriamiento'],
      repsCiclo:{},
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
      this.loading = true;
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
              switch (i) {
                case 0:
                  // eslint-disable-next-line no-case-declarations
                  const respCal = await cycleApi.add(respRut.id, {
                    name: "Calentamiento",
                    detail: "N/A",
                    type: "warmup",
                    order: 1,
                    repetitions:  parseInt(this.repsCiclo[i]),
                    metadata: null
                  }, null);
                  if (respCal.id) {
                    for (const ejercicio of this.ejsCycleAux[i]) {
                      await cycleExercisesApi.add(respCal.id, ejercicio.ej.id, { order: parseInt(ejercicio.orden), duration: parseInt(ejercicio.desc), repetitions: parseInt(ejercicio.reps)}, null);
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
                    repetitions:  parseInt(this.repsCiclo[i]),
                    metadata: null
                  }, null);
                  if (respEnfri.id) {
                    for (const ejercicio of this.ejsCycleAux[i]) {
                      await cycleExercisesApi.add(respEnfri.id, ejercicio.ej.id, { order: parseInt(ejercicio.orden), duration: parseInt(ejercicio.desc), repetitions: parseInt(ejercicio.reps)}, null);
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
                    repetitions: parseInt(this.repsCiclo[i]),
                    metadata: null
                  }, null);
                  if (respCiclo.id) {
                    for (const ejercicio of this.ejsCycleAux[i]) {
                      await cycleExercisesApi.add(respCiclo.id, ejercicio.ej.id, { order: parseInt(ejercicio.orden), duration: parseInt(ejercicio.desc), repetitions: parseInt(ejercicio.reps)}, null);
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
      console.log("CILCOS");
      console.log(ciclosAux);
      for (const h of ciclosAux.content) {
        console.log(h.id);
        var ejerCiclo = await cycleExercisesApi.getAll(h.id, null);
        console.log(ejerCiclo);
      }
    this.loading = false;
      await this.$store.dispatch("getCyclesOfID", rutinaAux.id);
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
    loadNextStepNewRut: async function () {
      let order = 1;
      let ejsArr;
      ejsArr = [];
      for (let i = 0; i < this.steps + 2; i++) {
        let auxArr;
        auxArr = [];
        for (const k of this.selected[i]) {
          auxArr.push({orden: order++, ej: this.ejercicios[k], reps: 0, desc: 0, sets: 0});
        }
        order = 1;
        //this.ejsCycleAux[i].push(this.ejercicios[k]);
        ejsArr[i] = auxArr;
        //}
      }
      console.log("Siguiente en ADD RUT");
      let cycloOf = await this.$store.state.cyclesOfRutine;
      console.log(cycloOf)
      console.log(ejsArr);
      this.$store.dispatch("setSelectedExercisesInCycles", ejsArr);
    },
    // cancelAUX: function (){
    //   console.log("ACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    //   console.log(this.$store.state.ejsCycleAux);
    // }
    },
  computed: {
    ejercicios(){
      return this.$store.state.listaEjercicios;
    },
    rutinas(){
      return this.$store.state.listaRutinas;
    },
    cardID(){
      return this.$store.state.cardID;
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
  mounted() {
    this.$store.dispatch("getExercises");
    // if(this.$store.state.listaRutinas.length !==0){
    //   this.$store.dispatch("getCyclesOfID", this.$store.state.listaRutinas[0].id);
    // }
    this.$store.dispatch("getCyclesOfID",1); /// VER DE DESSHAR
  }
}
</script>

<style scoped>

</style>