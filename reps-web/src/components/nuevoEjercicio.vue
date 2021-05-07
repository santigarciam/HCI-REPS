<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-btn depressed class="ma-2 mr-4" slot="activator" v-bind="attrs" v-on="on" outlined>+Anadir</v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Agregar nuevo ejercicio</h2>
      </v-card-title>

      <v-card-text>
        <v-form class="px-3">
          <v-text-field label="Nombre" v-model="nameEj"></v-text-field>
          <v-textarea label="Descripcion" v-model="detailEj" ></v-textarea>
          <v-text-field label="Materiales" v-model="matEj"></v-text-field>
          <v-file-input
              v-model="files"
              placeholder="Subi tu demostracion"
              label="Demostracion"
              multiple
              prepend-icon="mdi-paperclip"
          >
            <template v-slot:selection="{ text }">
              <v-chip
                  small
                  label
                  color="primary"
              >
                {{ text }}
              </v-chip>
            </template>
          </v-file-input>
        </v-form>
      </v-card-text>

      <v-col>
        <v-row>
          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
          <v-btn flat dark class="red mx-0" v-on:click.stop="">Cancelar</v-btn>
          <v-btn flat class="success mx-10" v-on:click="addNewExcercise">Guardar</v-btn>

        </v-row>
      </v-col>

    </v-card>
  </v-dialog>
</template>

<script>
import { ExerciseApi } from "@/API_EJS/js/exercises";
export default {

  name: "nuevaRutina",
  data(){
    return{
      nameEj:'',
      detailEj:'',
      matEj:''
    }
  },
  methods :{
    addNewExcercise: function (){
      ExerciseApi.add({name:this.nameEj,detail:this.detailEj,type:"exercise",metadata:null},null);

    }
  }
}
</script>

<style scoped>

</style>