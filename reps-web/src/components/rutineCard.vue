<template>
  <div class="text-center">
    <v-dialog
        width="900px"
        v-for="rutina in rutinas"
        :key="rutina.id"
        scrollable
    >
      <!--        v-for="rutina in data().rutinas" :key="rutina.tituloRut"  UNA LINEA MAS ARRIBA -->

      <template v-slot:activator="{ on, attrs }">
        <v-container class ="container_v_card pb-4">
          <v-card v-bind="attrs" v-on="on"  @click.stop="funcionAUX(rutina.id,rutina)"  :data="modalData" scrollable>
            <v-col>
              <v-row>
                <v-card-title prepend-icon="mdi-counter" class="mb-0 pb-0">{{ rutina.name }} </v-card-title>
                <v-spacer></v-spacer>
<!-- EDITAR RUTINAA-->
                  <v-dialog v-model="dialog[rutina.id]" width="900px" :retain-focus="false" >
                    <template  v-slot:activator="{ on, attrs }">
                      <v-btn  @click.stop="editarRutina(rutina.id)"  icon class="mt-4 mr-2" plain color = "grey" slot="activator" small  v-on:click.prevent="editarRutina(rutina.id)" v-bind="attrs" v-on="on">
                        <v-icon>
                          mdi-pencil
                        </v-icon>
                      </v-btn>
                    </template>

                    <v-card>

                      <v-card-title   class="white--text primary" > EDITAR RUTINA: {{  titleRut }}</v-card-title>
                      <!--        <v-btn v-on:click="getCiclosInID(parseInt(rutina.id))">BOTON</v-btn>-->
                      <v-divider></v-divider>
                      <v-col></v-col>
                      <v-card-subtitle>
                        <v-text-field  outlined label="Nombre:" v-model="rutAux.name"></v-text-field>
                      </v-card-subtitle>
                      <v-card-subtitle>
                      <v-text-field outlined label="Descripcion:" v-model="rutAux.detail"></v-text-field>
                      </v-card-subtitle>
                      <h4 class="pl-6 mb-4">Ciclos:</h4>

                      <v-expansion-panels  v-for="(ciclo,i) in cyclesAux" :key="ciclo.id">
                        <v-expansion-panel>
                          <v-row>
                            <v-col>
                              <v-expansion-panel-header v-if="i===0">Calentamiento </v-expansion-panel-header>
                              <v-expansion-panel-header v-else-if="i=== cyclesAux.length -1">Enfriamiento</v-expansion-panel-header>
                              <v-expansion-panel-header v-else> Ciclo {{i}}</v-expansion-panel-header>
                            </v-col>
                            <v-col align="center">

                              <v-text-field
                                  prepend-icon="mdi-counter"
                                  v-model="ciclo.repetitions"
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
<!--                            <v-chip class="mb-4 ma-2" small color="primary">x{{ciclo.repetitions}}</v-chip>-->
                            <template v-for="ejs in excercisesOfCycleAUX[i]">
                              <v-card small  class="mt-1" :key="ejs.exercise.id">

                                <v-row>
                                  <v-col>
                                    <v-card-text>{{ejs.exercise.name}}</v-card-text>
                                  </v-col> <v-col>
                                </v-col>
                                  <v-spacer></v-spacer>
                                  <v-col>
                                  </v-col>
                                  <v-col>

                                    <v-text-field
                                        prepend-icon="mdi-counter"
                                        v-model="ejs.duration"
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
                                        v-model="ejs.repetitions"
                                        class="mt-0 pt-0"
                                        hide-details
                                        single-line
                                        min = "1"
                                        type="number"
                                        style="width: 60px"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>

                              </v-card>
                            </template>
                          </v-expansion-panel-content>
                        </v-expansion-panel>
                      </v-expansion-panels>


                    <v-col>
                      <v-col></v-col>
                        <v-row>
                          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                          <v-btn plain color="grey" class="mx-0" @click="cancelActionRut">Cancelar</v-btn>
                          <v-btn :loading="loading" flat class="primary mx-10" @click="saveChanges()">Guardar</v-btn>

                        </v-row>
                      </v-col>

                      <v-col></v-col>
                    </v-card>
                  </v-dialog>
<!--                  EDITAR RUTINA FIN-->

                  <v-btn
                      icon
                      plain
                      color="grey"
                      v-model="boton"
                      class="mt-4 mr-2"
                      v-on:click="showSnackbar"
                  >
                    <v-icon class="pb-3">mdi-share</v-icon>
                  </v-btn>
                  <v-snackbar
                      v-model="snackbar"
                  >¡Se ha copiado al clipboard el link de la rutina!</v-snackbar>

                  <!--       Boton de borrar   -->
                  <v-dialog v-model="deleteConfi"  width="800px">
                    <template  v-slot:activator="{ on, attrs }">
                      <v-btn icon plain class="mt-4 mr-3" color = "red" slot="activator" small  v-bind="attrs" v-on="on">
                        <v-icon>
                          mdi-delete
                        </v-icon>
                      </v-btn>
                    </template>

                    <v-card>
                      <v-card-title>¿Está seguro que desea borrar esta rutina?</v-card-title>
                      <v-col text--center>
                        <v-row>
                          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                          <v-btn flat class="primary mx-0" @click="cancelActionRut">No</v-btn>
                          <v-btn :loading="loading" color="grey lighten-1 white--text mx-10" @click="deleteRut(rutina.id)">Si</v-btn>

                        </v-row>
                      </v-col>

                      <v-col></v-col>
                    </v-card>


                  </v-dialog>
                  <!--      Boton de borrar      -->

              </v-row>

<!--INFORMACION RUTINE CARD-->
            <v-row class="text-left">
              <v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripcion: </v-card-subtitle>
              <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
            </v-row>
            </v-col>

          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}<v-spacer></v-spacer><v-btn plain icon v-on:click="cancelActionRut"><v-icon dark>
          mdi-close
        </v-icon></v-btn></v-card-title>
        <v-divider></v-divider>
        <v-card-subtitle></v-card-subtitle>
        <v-card-subtitle>Descripcion: {{ rutina.detail }}</v-card-subtitle>
        <h4 class="pl-6 mb-4">Ciclos:</h4>

        <v-expansion-panels  v-for="(ciclo,i) in cyclesOfRutine" :key="ciclo.id">
                <v-expansion-panel>
                  <v-expansion-panel-header>
                    {{ciclo.name}}
                  </v-expansion-panel-header>
                  <v-expansion-panel-content>
                    <v-chip class="mb-4 ma-2" small color="primary">x{{ciclo.repetitions}}</v-chip>
                <template v-for="ejs in exercisesOfCycle[i]">

                          <v-card small  class="mt-1" :key="ejs.exercise.id">

                              <v-row>
                                <v-col>
                            <v-card-text>{{ejs.exercise.name}}</v-card-text>
                                </v-col> <v-col>
                                </v-col>
                              <v-spacer></v-spacer>
                                <v-col>
                              <v-card-text> Duracion: {{ejs.duration}}</v-card-text>
                                </v-col>
                                <v-col>
                              <v-card-text> REPS: {{ejs.repetitions}}</v-card-text>
                                </v-col>
                              </v-row>

                          </v-card>

                </template>
                  </v-expansion-panel-content>
                </v-expansion-panel>
          </v-expansion-panels>
      </v-card>

    </v-dialog>
  </div>
</template>




<script>
import NuevaRutina from "@/components/nuevoEjercicio";
import  EditRutina from "@/components/editRut"

import { routineApi } from "../API_EJS/js/routines";
import { cycleApi } from "../API_EJS/js/cycles";
import {cycleExercisesApi} from "../API_EJS/js/cycleExercises";


export default {
  componets: {NuevaRutina, EditRutina},
  data() {
    return {
      loading: false,
      snackbar: false,
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
        loading: false,
      }
    },
    showSnackbar: function (event) {
      event.stopPropagation();
      console.log(this);
     // alert("PRUEBA");
        this.snackbar = true;
        setTimeout(() => {
          this.$emit("yourEvent");
        }, this.timeout);
      },
    deleteRut: async function (id) {
      this.loading = true;
      const cyclesIDaux = await cycleApi.getAll(id, null);
      for (const ciclo of cyclesIDaux.content) {
        const ejercicios = await cycleExercisesApi.getAll(ciclo.id,null);
        let ejs = ejercicios.content.exercise;
        for (const ej in ejs){
          await cycleExercisesApi.delete(ciclo.id, ej.id, null);
        }
        // DESCOMENTAR CUANDO LA API FUNCIONE BIEN  !!!!!!!!!!!!!!!!!!
      }
      await routineApi.delete(id);
      this.loading = false;
      this.cancelActionRut();
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
    editarRutina: async function(rutID){
      const resp = await routineApi.get(rutID,null);
      if(resp.id){
        this.titleRut = resp.name;
        this.rutAux = resp;
        await this.$store.dispatch("getCyclesOfID", resp.id);
        this.cyclesAux = await this.$store.state.cyclesOfRutine;
        this.excercisesOfCycleAUX =  this.$store.state.exersisesOfRoutineOnCycle;
        console.log("ANTES")
        console.log(this.excercisesOfCycleAUX)
        for(var i=0;i<this.excercisesOfCycleAUX.length;i++){
          this.excercisesOfCycleAUX[i].orderBy(ej => ej.id);
        }

        console.log("DESPUESS")
        console.log(this.excercisesOfCycleAUX)
      }
    },
    saveChanges: async function () {

      console.log(this.rutAux);
        let respExCycle;
        console.log('Editandoo');
          for(var i=0;i<this.cyclesAux.length ; i++){
            let cycleAux;
            cycleAux = {id:parseInt(this.cyclesAux[i].id),name:this.cyclesAux[i].name,detail:this.cyclesAux[i].detail,type:this.cyclesAux[i].type,order:parseInt(this.cyclesAux[i].order),repetitions: parseInt(this.cyclesAux[i].repetitions) ,metadata:this.cyclesAux[i].metadata};
            console.log("ciclo a mod")
              for(var j=0; j< this.excercisesOfCycleAUX[i].length;j++) {
                let ejAux;
                ejAux = {id:parseInt(this.excercisesOfCycleAUX[i][j].exercise.id),order: parseInt(this.excercisesOfCycleAUX[i][j].order),duration: parseInt(this.excercisesOfCycleAUX[i][j].duration),repetitions:parseInt(this.excercisesOfCycleAUX[i][j].repetitions)};
               console.log("Ejercicio a mod");
                respExCycle = await cycleExercisesApi.delete(parseInt(this.cyclesAux[i].id), parseInt(ejAux.id), null);
                respExCycle = await cycleExercisesApi.add(parseInt(this.cyclesAux[i].id),parseInt(ejAux.id),{order:ejAux.order,duration:ejAux.duration,repetitions:ejAux.repetitions},null);
                if (!respExCycle.order) {
                  console.log("ERROR");
                }
              }
            await cycleApi.modify(this.rutAux.id,cycleAux,null);

        }
      await routineApi.modify(this.rutAux, null);

      }
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
      // if (this.$store.state.listaRutinas.length !== 0) {
      //   await this.$store.dispatch("getCyclesOfID", this.$store.state.listaRutinas[0].id);
      // }
      // console.log(this.$store.state.listaRutinas)
    },
}
</script>