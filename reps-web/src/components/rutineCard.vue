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
          <v-card v-bind="attrs" v-on="on"  @click.stop="funcionAUX(rutina.id,rutina)"  :data="modalData" >
            <v-col>
              <v-row>
                <v-card-title>{{ rutina.name }} </v-card-title>
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
                      color="grey"
                      v-model="boton"
                      class="mt-4 mr-2"
                      v-on:click="showSnackbar"

                  >
                    <v-icon>mdi-share</v-icon>
                  </v-btn>
                  <v-snackbar
                      v-model="snackbar"
                      :timeout="timeout"
                  >Se copio al clipboard el link de la rutina!</v-snackbar>

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
                      <v-card-title>¿Está seguro que desea borrar esta rutina?</v-card-title>
                      <v-col text--center>
                        <v-row>
                          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
                          <v-btn dark flat class="primary mx-0" @click="cancelActionRut">No</v-btn>
                          <v-btn :loading="loading" plain color="grey" class="mx-10" @click="deleteRut(rutina.id)">Si</v-btn>

                        </v-row>
                      </v-col>

                      <v-col></v-col>
                    </v-card>


                  </v-dialog>
                  <!--      Boton de borrar      -->

              </v-row>

            </v-col>
            <v-col class="text-left">
<!--              <v-card-subtitle v-model="autorRut">Autor: {{ HACERFUNCIONDECURRENTUSER }} </v-card-subtitle>-->
              <v-card-subtitle v-model="descripcionRut">Descripcion: {{ rutina.detail }}</v-card-subtitle>
<!--              <v-card-subtitle v-model="durRut">Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
            </v-col>


          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}</v-card-title>
<!--        <v-btn v-on:click="getCiclosInID(parseInt(rutina.id))">BOTON</v-btn>-->
        <v-divider></v-divider>
        <v-card-subtitle></v-card-subtitle>
        <v-card-subtitle>Descripcion: {{ rutina.detail }}</v-card-subtitle>
<!--        <v-card-subtitle>Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
        <h4 class="pl-6">Ciclos:</h4>
        <template v-for="ciclo in cyclesOfRutine">
          <v-list-item :key="ciclo.id">
            <template>
              <v-list-item-content>
                <v-list-item-title class="pl-6" v-text="ciclo.name"></v-list-item-title>

              </v-list-item-content>
            </template>
          </v-list-item>
        </template>
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
  data () {
    return {
      snackbar: false,
      dialog:{id:0,on:false},
      loading: false,
      modalVisible:null,
      dialogEdit:false,
      rutAux:{id:0,name:'',detail:'',category:{name:'',id:0}},
    }
  },
  methods: {
    data: function ()  {
      return {
      modalData: null,
      }
    },

    showSnackbar: function (event) {
      event.stopPropagation();
      console.log(this);
      alert("PRUEBA");
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
        for (const ej of ejercicios){
          await cycleExercisesApi.delete(ciclo.id, ej.id, null);
        }
        // DESCOMENTAR CUANDO LA API FUNCIONE BIEN !!!!!!!!!!!!!!!!!!
        await cycleApi.delete(id,ciclo,null);
      }
      await routineApi.delete(id);
      this.loading = false;
      await this.$store.dispatch("changeCardID");//es como un flag que avisa un cambio de estado
    },
    cancelActionRut: function (){
      this.$store.dispatch("changeCardID"); //es como un flag que avisa un cambio de estado
    },
    //////////////////////////////////////////////////////////////////////////////////
    funcionAUX: async function (id,item){
      console.log("acaaa");
      console.log(id);
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
        console.log(respCat);
        this.rutAux.category.name = respCat.name;
      }
    }
    /////////////////////////////////////////////////////////////////////////////////
    },

    computed: {
      rutinas(){
        return this.$store.state.listaRutinas;
      },
      cardID(){
        return this.$store.state.cardID;
      },
      cyclesOfRutine(){
        console.log(this.$store.state.cyclesOfRutine);
        return this.$store.state.cyclesOfRutine;
      }
    },

    mounted() {
      this.$store.dispatch("getUserRoutines");
      console.log(this.$store.state.listaRutinas)
    },
}
</script>