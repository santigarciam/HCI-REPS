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
                <v-card-title class="mb-0 pb-0">{{ rutina.name }}
                  <v-rating
                      v-model="rutina.rating"
                      half-increments
                      readonly
                      background-color="grey"
                      color="orange"
                      medium
                      class="ml-2"
                  ></v-rating></v-card-title>
                <v-spacer></v-spacer>
                <div>
                  <v-btn
                      icon
                      color="grey"
                      v-model="boton"
                      plain
                      class="mt-4 mr-2"
                      v-on:click="showSnackbar"
                  >
                    <v-icon>mdi-share</v-icon>
                  </v-btn>

                  <v-snackbar
                      v-model="snackbar"
                  >¡Se ha copiado al clipboard el link de la rutina!</v-snackbar>

                  <v-btn class="mt-4 mr-3" plain icon v-on:click="changeFav(rutina.id)" color="red">
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>

                </div>
              </v-row>

              <!--INFORMACION RUTINE CARD-->
              <v-row class="text-left">
                <v-icon small class="align-center mr-0 ml-3" color="blue">mdi-account</v-icon>
                <v-card-subtitle class="blue--text ml-0 pl-1 mt-0 pt-0 pb-0 font-weight-bold" v-model="autorRut">{{ rutina.user.username }} </v-card-subtitle>
              </v-row>
              <v-row><v-card-subtitle class="mr-0 pr-0 font-weight-bold">Descripción: </v-card-subtitle>
                <v-card-subtitle class="ml-0 pl-1" v-model="descripcionRut">{{ rutina.detail }}</v-card-subtitle>
              </v-row>
            </v-col>

          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}<v-spacer></v-spacer><v-btn icon plain v-on:click="cancelActionRut"><v-icon dark>
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

export default {

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

    changeFav: function (id) {
      console.log("entra")
      console.log("le manda" + id)
      if (this.idFavoritas.includes(id)){
        this.$store.dispatch("deleteFavourites", id);
      }
      else {
        this.$store.dispatch("addFavourites", id);
      }
      //para actualizar los valores
      this.$store.dispatch('changeCardID');
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
    /////////////////////////////////////////////////////////////////////////////////
  },

  computed: {
    rutinas(){
      return this.$store.state.rutinasFavoritas;

    },
    idFavoritas(){
      return this.$store.state.idFavoritas;
    },
    cardID(){
      return this.$store.state.cardID;
    },
    ejercicios(){
      return this.$store.state.listaEjercicios;
    },
    // cyclesOfRutine(){
    //   console.log(this.$store.state.cyclesOfRutine);
    //   return this.$store.state.cyclesOfRutine;
    // },
    exercisesOfCycle(){
      return this.$store.state.exersisesOfRoutineOnCycle;
    } ////// DESCOMENTAR CUANDO EL API FUNCIONE
  },

  beforeCreate() {
    this.$store.dispatch("getFavourites", "");
    // console.log("1")
  },
}
</script>