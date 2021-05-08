<template>
  <div class="text-center">
  <v-dialog v-model="dialog" width="800px" v-for="excercise in ejercicios " :key="excercise.id" >
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-card depressed class="ma-2" slot="activator" v-bind="attrs" v-on="on">
        <v-col>
          <v-row>
            <v-card-title>{{ excercise.name }}</v-card-title>
            <v-spacer></v-spacer>
            <edit-ej v-on:click="modifyExercise(excercise)"></edit-ej>
<!--        <edit-ej ></edit-ej>      -->
            <v-dialog v-model="deleteConfi" width="800px">
              <template  v-slot:activator="{ on, attrs }">
                <v-btn icon class="mt-4 mr-3" plain color = "red" slot="activator" small  v-bind="attrs" v-on="on">
                  <v-icon>
                    mdi-delete
                  </v-icon>
                </v-btn>
              </template>

              <v-card>
                <v-card-title>Estas seguro que quiere borrar?</v-card-title>
                <v-col text--center>
                  <v-row>
                    <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                    <v-btn dark flat class="red mx-0" @click="cancelAction">No</v-btn>
                    <v-btn flat class="success mx-10" @click="deleteEj(excercise.id)">Si</v-btn>

                  </v-row>
                </v-col>

                <v-col></v-col>
              </v-card>


            </v-dialog>
            <!--            -->


          </v-row>
        </v-col>

      </v-card>
    </template>

    <v-card>
      <v-card-title>
        <h2>{{ excercise.name }}</h2>
      </v-card-title>

      <v-card-text>
        <v-card-title v-model="descripcionEj">Descripcion: {{ excercise.detail }}</v-card-title>
        <v-card-title v-model="multEj">Multimedia:</v-card-title>
        <v-card-title v-model="equipEj">Equipamiento:</v-card-title>
      </v-card-text>


    </v-card>
  </v-dialog>
  </div>
</template>

<script>
import EditEj from "@/components/editEj";
import { ExerciseApi } from "@/API_EJS/js/exercises";
//import DeleteConfirmaticon from "@/components/deleteConfirmation";
//import {ExerciseApi} from "@/API_EJS/js/exercises";
export default {
  components: { EditEj },
  data: () => ({

}),
  methods: {
    modifyExercise: function (excercise){
      ExerciseApi.modify(excercise);
    },
    deleteEj: function (id){
      ExerciseApi.delete(id);
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    cancelAction: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    }

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