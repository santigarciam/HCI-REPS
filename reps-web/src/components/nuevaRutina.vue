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
        <v-form class="px-3">
          <v-text-field outlined label="Nombre" v-model="nameRut"></v-text-field>
          <v-textarea outlined auto-grow label="Descripcion" v-model="detailRut" ></v-textarea>
          <v-text-field outlined label="Duracion" v-model="durRut"></v-text-field>

          <v-col>
            <v-row>
          <v-card-title class="text--black">
            Ciclos de entrenamiento
            <v-select
                :items="ciclos"
                label="Ciclo"
                dense
                class="mt-6 ml-4"
                outlined
            ></v-select>
          </v-card-title>
            </v-row>
          </v-col>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field label="Repeticiones:" v-model="repsEj"></v-text-field>
              </v-col>
              <v-col>
                <v-text-field label="Descanso entre repeticiones:" v-model="descReps"></v-text-field>
              </v-col>
              <v-col>
                <v-text-field label="Descanso entre ejercicios:" v-model="descReps"></v-text-field>
              </v-col>
            </v-row>
          </v-container>
          <v-col class="text-center">
            <v-dialog>
              <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
                <v-btn  slot="activator" color="grey lighten-1" v-bind="attrs" v-on="on" >Ejercicios</v-btn>
              </template>

              <v-card>
                <v-card-title>HAY QUE PONER LA LISTA DE EJERCICIOS Y VER COMO SE SELECCIONAN</v-card-title>
              </v-card>
            </v-dialog>
          </v-col>

          <v-col></v-col>
          <v-col>
            <v-row>
              <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
              <v-btn flat dark class="red mx-0" v-on:click="submit">Cancelar</v-btn>
              <v-btn flat class="success mx-10" v-on:click="addNewRoutine">Guardar</v-btn>

            </v-row>
          </v-col>

        </v-form>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>

import rutineCard from "./rutineCard.vue";
import  { routineApi } from "@/API_EJS/js/routines"
export default {
  name: "nuevaRutina",
  data(){
    return{
      nameRut:'',
      detailRut:'',
      durRut:'',
      ciclos: ['Entrada en calor', 'Entrenamiento', 'Enfriamiento'],
    }
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
    }
  }
}
</script>

<style scoped>

</style>