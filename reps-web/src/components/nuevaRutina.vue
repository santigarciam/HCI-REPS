<template>

  <v-dialog v-model="dialog" width="900px">
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
                            v-model="selected"
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
                <!--             DATA STEEPPER CALENTAMIENTO -->
                <v-stepper-content
                    :key="`enfriamiento-content`"
                    :step="steps+1"
                >
                  <v-card
                      class="mb-12"
                      color="grey lighten-1"
                      height="200px"
                  ></v-card>

                </v-stepper-content>

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
                          v-model="selected"
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
              </v-stepper-items>
            </v-stepper>
          </div>

        <v-col></v-col>
                  <v-col>
                    <v-row>
                      <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                      <v-btn flat dark class="red mx-0" v-on:click="submit">Cancelar</v-btn>
                      <v-btn flat class="success mx-10" v-on:click="addNewRoutine">Guardar</v-btn>

                    </v-row>
                  </v-col>
              </v-card-text>
            </v-card>
  </v-dialog>
</template>

<script>

import rutineCard from "./rutineCard.vue";
import  { routineApi } from "@/API_EJS/js/routines"
//import state from "/"

export default {
  name: "nuevaRutina",
  components: {},
  data(){
    return{
      nameRut:'',
      detailRut:'',
      e1: 1,
      steps: 2,
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
    submit: function (event){
      if (event){
        rutineCard.methods.newRutine(this.nombreRut,"YO",this.descripcionRut,this.durRut, "0");
      }

      // generarRutinaNueva(tituloRut, autorRut, descripcionRut, durRut, rating)
    },
    addNewRoutine: function(){
      routineApi.add({name:this.nameRut,detail:this.detailRut,isPublic:true,difficulty:"rookie",category:{ id:1},metadata:null},null);
    },
    },
  computed: {
    ejercicios(){
      return this.$store.state.listaEjercicios;
    }
  },
  mounted() {
    this.$store.dispatch("getExercises");
  }
}
</script>

<style scoped>

</style>