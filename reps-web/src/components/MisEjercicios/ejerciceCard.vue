<template>
  <div class="text-center">
  <v-dialog v-model="dialogEJ" width="800px" v-for="excercise in ejercicios " :key="excercise.id" >
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-container class ="container_v_card">
      <v-card depressed slot="activator" v-bind="attrs" v-on="on">
        <v-col>
          <v-row>
            <v-card-title>{{ excercise.name }}</v-card-title>
            <v-spacer></v-spacer>
<!--            EDITAR EJERCICIO-->
<!--            <v-dialog v-model="dialog[excersise.id]" width="800px" :retain-focus="false" >-->
            <v-dialog persistent v-model="dialog[excercise.id]" width="800px" :retain-focus="false" >
            <template  v-slot:activator="{ on, attrs }">
              <v-btn icon class="mt-4 mr-2" plain color = "grey" slot="activator" small  v-on:click.prevent="editEj(excercise.id)" v-bind="attrs" v-on="on">
                <v-icon>
                  mdi-pencil
                </v-icon>
              </v-btn>
            </template>

            <v-card>
              <v-card-title class="justify-center white--text primary" >EDITAR EJERCICIO: {{nameEdited}}</v-card-title>
              <v-card-text>
                <v-col></v-col>
                <v-form class="px-3">
                  <v-text-field outlined label="Nombre*" v-model.lazy="excerciseAux.name"></v-text-field>
                  <v-textarea  outlined label="Descripcion*" v-model="excerciseAux.detail" ></v-textarea>

                </v-form>
              </v-card-text>
              <v-col>
                <v-row>
                  <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                  <v-btn color="grey lighten-1 white--text mx-0" @click="cancelAction">Cancelar</v-btn>
                  <v-btn flat class="primary mx-10" @click="modifyExercise(excercise)">Guardar</v-btn>
                </v-row>
              </v-col>
              <v-col></v-col>
            </v-card>
          </v-dialog>

            <!--            EDITAR EJERCICIO-->
<!--       Boton de borrar   -->
            <v-dialog v-model="deleteConfi"  width="800px">
              <template  v-slot:activator="{ on, attrs }">
                <v-btn icon class="mt-4 mr-3" plain color = "red" slot="activator" small  v-bind="attrs" v-on="on">
                  <v-icon>
                    mdi-delete
                  </v-icon>
                </v-btn>
              </template>

              <v-card>
                <v-card-title><div><span>¿Está seguro que desea borrar el ejercicio </span><span style="color: #258be5;">"{{excercise.name}}"</span>?</div></v-card-title>
                <v-col text--center>
                  <v-row>
                    <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                    <v-btn dark flat class="primary mx-0" @click="cancelAction">No</v-btn>
                    <v-btn color="grey lighten-1 white--text" class="mx-10" @click="deleteEj(excercise.id)">Si</v-btn>

                  </v-row>
                </v-col>

                <v-col></v-col>
              </v-card>


            </v-dialog>
            <!--      Boton de borrar      -->


          </v-row>
        </v-col>

      </v-card>
      </v-container>
    </template>

    <v-card>
      <v-card-title>
        {{ excercise.name }}
        <v-spacer></v-spacer>
        <v-btn plain v-on:click="cancelAction"><v-icon dark>
        mdi-close
      </v-icon></v-btn>

      </v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <v-card-title v-model="descripcionEj">Descripcion:</v-card-title>
        <v-card-text>{{ excercise.detail }}</v-card-text>
<!--        <v-card-title v-model="multEj">Multimedia:</v-card-title>-->
<!--        <v-card-title v-model="equipEj">Equipamiento:</v-card-title>-->
      </v-card-text>


    </v-card>
  </v-dialog>
  </div>
</template>

<script>

import { ExerciseApi } from "@/API_EJS/js/exercises";

export default {
  components: {},
  data (){
    return {
      nameEdited: '',
      dialog:{id:0,on:false},
      detailEdited:'',
      currentID:-1,
      // dialog: false,
      dialogEditRut: false,
      excerciseAux:{id:0,name:'',detail:''},
      loading: false,
    }
},
  methods: {
    modifyExercise: async function (excercise){
      this.loading = true;
      excercise.name = this.excerciseAux.name;
      excercise.detail = this.excerciseAux.detail;
     const resp = await ExerciseApi.modify(excercise);
     if(resp.id){
       console.log("se logro modificar el ej");
     }else{
       console.log(" NO se logro modificar el ej");
     }
      this.dialog[excercise.id] = false;

      await this.$store.dispatch("changeCardID");
      this.loading = false;
     return resp;
    },
    deleteEj: function (id){
      ExerciseApi.delete(id);
      this.$store.dispatch("changeCardID");//es como un flag que avisa un cambio de estado
    },
    cancelAction: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    editEj: async function (excersiseID){
      const resp = await ExerciseApi.get(excersiseID,null);
      if(resp.id){
        this.nameEdited = resp.name;
      this.excerciseAux.id = resp.id;
      this.excerciseAux.name = resp.name;
      this.excerciseAux.detail = resp.detail;
      }
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