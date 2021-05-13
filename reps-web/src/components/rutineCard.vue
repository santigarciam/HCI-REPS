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
                <v-card-title class="mb-0 pb-0">{{ rutina.name }} </v-card-title>
                <v-spacer></v-spacer>
<!-- EDITAR RUTINAA-->
                  <v-dialog v-model="dialog[rutina.id]" width="800px" :retain-focus="false" >
                    <template  v-slot:activator="{ on, attrs }">
                      <v-btn icon class="mt-4 mr-2" plain color = "grey" slot="activator" small  v-on:click.prevent="editarRutina(rutina.id)" v-bind="attrs" v-on="on">
                        <v-icon>
                          mdi-pencil
                        </v-icon>
                      </v-btn>
                    </template>

                    <v-card>
                      <v-card-title>Editar Rutina {{rutina.id}}</v-card-title>


                      <v-card-text>

                        <v-form class="px-3">
                          <v-text-field outlined label="Nombre*" v-model="rutAux.name"></v-text-field>
                          <v-textarea  outlined label="Descripcion*" v-model="rutAux.detail" ></v-textarea>
                          <v-text-field outlined label="Categoría" v-model="rutAux.category.name"></v-text-field>

                        </v-form>
<!--                        Ciclos en EDITAR RUT-->



                        <v-stepper v-model="e1">
                          <v-stepper-header>
                            <template v-for="(ciclo,n) in cyclesOfRutine">
                              <v-divider
                                  v-if="n !== steps"
                                  :key="ciclo.id"
                              ></v-divider>
                              <v-stepper-step
                                  :key="ciclo.id"
                                  :step="n"
                                  editable
                              >
                              {{ciclo.name}}
                              </v-stepper-step>
                              <v-divider
                                  v-if="n !== steps"
                                  :key="ciclo.id"
                              ></v-divider>
                            </template>
                          </v-stepper-header>
                        </v-stepper>


                        <!--                          <v-stepper-items>-->
<!--                            <v-stepper-content-->
<!--                                v-for="(ciclo,n) in cyclesOfRutine"-->
<!--                                :key="ciclo.id"-->
<!--                                :step="n"-->
<!--                            >-->
<!--                              <v-card-->
<!--                                  class="mb-12"-->
<!--                                  color="grey lighten-1"-->
<!--                                  height="200px"-->
<!--                              >-->
<!--                                <v-list two-line  style="max-height:250px"-->
<!--                                        class="overflow-y-auto">-->
<!--                                  <v-list-item-group-->
<!--                                      v-model="selected[n]"-->
<!--                                      active-class="grey&#45;&#45;text"-->
<!--                                      multiple-->
<!--                                  >-->
<!--                                    <template v-for="(excersise, index) in exercisesOfCycle(n) ">-->
<!--                                      <v-list-item :key="excersise.id">-->
<!--                                        <template v-slot:default="{ active }">-->
<!--                                          <v-list-item-content>-->
<!--                                            <v-list-item-title v-text="excersise.name"></v-list-item-title>-->

<!--                                          </v-list-item-content>-->

<!--                                          <v-list-item-action>-->

<!--                                            <v-icon-->
<!--                                                v-if="active"-->
<!--                                                color="grey lighten-1"-->
<!--                                            >-->
<!--                                              mdi-check-->
<!--                                            </v-icon>-->

<!--                                          </v-list-item-action>-->
<!--                                        </template>-->
<!--                                      </v-list-item>-->

<!--                                      <v-divider-->
<!--                                          v-if="index < excersise.length - 1"-->
<!--                                          :key="index"-->
<!--                                      ></v-divider>-->
<!--                                    </template>-->
<!--                                  </v-list-item-group>-->

<!--                                </v-list>-->


<!--                              </v-card>-->
<!--                            </v-stepper-content>-->
<!--                          </v-stepper-items>-->


<!--                        CICLO EDITAR RUT FIN-->
                      </v-card-text>
                      <v-col>
                        <v-row>
                          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                          <v-btn plain color="grey" class="mx-0" @click="cancelActionRut">Cancelar</v-btn>
                          <v-btn :loading="loading" flat class="primary mx-10" @click="modifyRut(rutina)">Guardar</v-btn>

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
                  >Se copio al clipboard el link de la rutina!</v-snackbar>

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
                          <v-btn :loading="loading" plain color="grey" class="mx-10" @click="deleteRut(rutina.id)">Si</v-btn>

                        </v-row>
                      </v-col>

                      <v-col></v-col>
                    </v-card>


                  </v-dialog>
                  <!--      Boton de borrar      -->

              </v-row>

<!--INFORMACION RUTINE CARD-->
            <v-row class="text-left">
<!--              <v-card-subtitle v-model="autorRut">Autor: {{ HACERFUNCIONDECURRENTUSER }} </v-card-subtitle>-->
              <v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripcion: </v-card-subtitle>
              <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
            </v-row>
            </v-col>

          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}<v-spacer></v-spacer><v-btn plain v-on:click="cancelActionRut"><v-icon dark>
          mdi-close
        </v-icon></v-btn></v-card-title>
<!--        <v-btn v-on:click="getCiclosInID(parseInt(rutina.id))">BOTON</v-btn>-->
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
<!--                <v-card :key="ejs">-->
<!--                  <v-card-title>ENTRO</v-card-title>-->
<!--                  <template v-for="ej in ejs">-->
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
<!--                  </template>-->
<!--                </v-card>-->
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
// import EditRut from "@/components/editRut";
import { routineApi } from "../API_EJS/js/routines";
import { cycleApi } from "../API_EJS/js/cycles";
import {cycleExercisesApi} from "../API_EJS/js/cycleExercises";
import {categoryApi} from "../API_EJS/js/category";
//import {cycleExercisesApi } from "../API_EJS/js/cycleExercises";

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
    funcionAUX2: function (){
      console.log("llegooooooooooooooooooooo");
      return this.cyclesOfRutine;
    },

    editarRutina: async function(rutID){
      console.log('entrooooooo');
      console.log(this);


      const resp = await routineApi.get(rutID,null);
      if(resp.id){
        this.rutAux.id = resp.id;
        this.rutAux.name = resp.name;
        this.rutAux.detail = resp.detail;
        const respCat = await categoryApi.get(resp.category.id,null);
        await this.$store.dispatch("getCyclesOfID", resp.id);

        console.log(respCat);
        this.rutAux.category.name = respCat.name;
      }
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
      } ////// DESCOMENTAR CUANDO EL API FUNCIONE
    },

    mounted() {
      this.$store.dispatch("getUserRoutines");
      // if(this.$store.state.listaRutinas.length !==0){
      //    this.$store.dispatch("getCyclesOfID", this.$store.state.listaRutinas[0].id);
      // }
     // console.log(this.$store.state.listaRutinas)
    },
}
</script>