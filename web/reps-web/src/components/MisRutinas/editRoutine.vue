<template>
  <v-dialog v-model="dialog[rutina.id]" width="900px" :retain-focus="false" >
<template  v-slot:activator="{ on, attrs }">
  <v-btn  icon class="mt-4 mr-2" plain color = "grey" slot="activator" small  v-on:click.prevent="editarRutina(rutina)" v-bind="attrs" v-on="on">
    <v-icon>
      mdi-pencil
    </v-icon>
  </v-btn>
</template>

<v-card>

  <v-card-title class="align-center justify-center white--text primary" >EDITAR RUTINA: {{  titleRut }}</v-card-title>

  <v-divider></v-divider>
  <v-col></v-col>
  <v-card-text class="mt-6">
  <v-row>
    <v-text-field dense class="mr-3 ml-3" outlined label="Nombre:" v-model="rutAux.name"></v-text-field>
    <v-btn outlined class="align-center mr-3" @click="changePrivacy()"><v-icon class="mr-2">{{this.lock}}</v-icon>{{this.privacy}}</v-btn>
  </v-row>
  <v-row>
    <v-textarea outlined dense auto-grow class="mr-3 ml-3" label="Descripción:" v-model="rutAux.detail"></v-textarea>
  </v-row><v-row>

      <v-col>  <v-select
          v-model="diff"
          :items="dificultad"
          label="Dificultad"
          outlined dense
          item-text="show"
          item-value="value"
          :menu-props="{ maxHeight: '400' }"
      ></v-select></v-col></v-row>

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

          <v-col><v-subheader>Repeticiones del ciclo:
            <v-text-field
                append-icon="mdi-counter"
                v-model="ciclo.repetitions"
                class="mt-0 pt-0"
                hide-details
                single-line
                min = "1"
                type="number"
                style="width: 80px"
            ></v-text-field>
          </v-subheader>
          </v-col>
        </v-col>
      </v-row>
      <v-expansion-panel-content>

        <template v-for="ejs in excercisesOfCycleAUX[i]">
          <v-card small  class="mt-1" :key="ejs.exercise.id">

            <v-container>
              <v-row>
                <v-col>
                  <v-card-text>{{ejs.exercise.name}} </v-card-text>
                </v-col>

                <v-col><v-subheader>Duración:
                  <v-text-field
                      append-icon="mdi-clock-outline"
                      v-model="ejs.duration"
                      class="mt-0 pt-0"
                      hide-details
                      single-line

                      min = "1"
                      type="number"
                      style="width: 80px"
                  ></v-text-field>
                </v-subheader>
                </v-col>
                <v-col><v-subheader>Repeteciones:
                  <v-text-field
                      append-icon="mdi-counter"
                      v-model="ejs.repetitions"
                      class="mt-0 pt-0"
                      hide-details
                      single-line
                      min = "1"
                      type="number"
                      style="width: 80px"
                  ></v-text-field>
                </v-subheader>
                </v-col>

              </v-row>
            </v-container>
          </v-card>
        </template>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>


  <v-col>
    <v-col></v-col>
    <v-row>
      <v-spacer></v-spacer>
      <v-btn  color="grey lighten-1 white--text mx-0" @click="cancelActionRut">Cancelar</v-btn>
      <v-btn :loading="loading" color="#2679CC" dark class=" mx-10" @click="saveChanges">Guardar</v-btn>

    </v-row>
  </v-col>

  <v-col></v-col>

  </v-card-text>
</v-card>
</v-dialog>
</template>


<script>
import {cycleExercisesApi} from "../../API_EJS/js/cycleExercises";
import {cycleApi} from "../../API_EJS/js/cycles";
import {routineApi} from "../../API_EJS/js/routines";

export default {
  props: ['rutina'],
  data(){
    return{
      lock: "",
      isPublic: '',
      dialog:{id:0},
      rutAux:{id:0,name:'',detail:'',category:{name:'',id:0}},
      titleRut:'',
      cyclesAux:[],
      excercisesOfCycleAUX:[],
      diff: "",
      dificultad: [
        {show:'Novato', value:'rookie' },
        {show:'Principiante', value: 'beginner' },
        {show:'Intermedio', value:'intermediate' },
        {show:'Avanzado', value:'advanced'},
        {show:'Experto', value:'expert'},],

    }
  },
  computed: {
    privacy() {
      if(this.isPublic){
        return "Pública"
      }
      else {
        return "Privada"
      }
    }
  },
  methods:{
    changePrivacy: function (){
      if(this.isPublic){
        this.lock="mdi-lock"
        this.isPublic=false
      }
      else{
        this.lock="mdi-lock-open"
        this.isPublic=true
      }
    },
    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    editarRutina: async function(rutina){
         this.titleRut = rutina.name;
        this.rutAux = rutina;
        this.diff = rutina.difficulty;
        this.isPublic = rutina.isPublic;
        this.lock = (this.isPublic) ? "mdi-lock-open" : "mdi-lock";
        this.cyclesAux =rutina.ciclosRut;
        let i =0;
        for(const ciclo of this.cyclesAux) {
          this.excercisesOfCycleAUX[i++] = ciclo.ciclosEjs;
        }
      },
    saveChanges: async function () {
      this.rutAux.isPublic=this.isPublic
      this.rutAux.difficulty=this.diff
      console.log(this.rutAux);
      let respExCycle;
      for(var i=0;i<this.cyclesAux.length ; i++){
        let cycleAux;
        cycleAux = {id:parseInt(this.cyclesAux[i].id),name:this.cyclesAux[i].name,detail:this.cyclesAux[i].detail,type:this.cyclesAux[i].type,order:parseInt(this.cyclesAux[i].order),repetitions: parseInt(this.cyclesAux[i].repetitions) ,metadata:this.cyclesAux[i].metadata};
        for(var j=0; j< this.excercisesOfCycleAUX[i].length;j++) {
          let ejAux;
          ejAux = {id:parseInt(this.excercisesOfCycleAUX[i][j].exercise.id),order: parseInt(this.excercisesOfCycleAUX[i][j].order),duration: parseInt(this.excercisesOfCycleAUX[i][j].duration),repetitions:parseInt(this.excercisesOfCycleAUX[i][j].repetitions)};

          respExCycle = await cycleExercisesApi.delete(parseInt(this.cyclesAux[i].id), parseInt(ejAux.id), null);
          respExCycle = await cycleExercisesApi.add(parseInt(this.cyclesAux[i].id),parseInt(ejAux.id),{order:ejAux.order,duration:ejAux.duration,repetitions:ejAux.repetitions},null);
          if (!respExCycle.order) {
            console.log("ERROR");
          }
        }
        await cycleApi.modify(this.rutAux.id,cycleAux,null);

      }
      await routineApi.modify(this.rutAux, null);
      this.cancelActionRut();

    }
  }
}
</script>