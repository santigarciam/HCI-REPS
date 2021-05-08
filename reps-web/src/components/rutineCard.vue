<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        width="500"
        v-for="rutina in rutinas.content" :key="rutina.name"
    >
      <!--        v-for="rutina in data().rutinas" :key="rutina.tituloRut"  UNA LINEA MAS ARRIBA -->
      <template v-slot:activator="{ on, attrs }">
        <v-container class ="container_v_card pb-4">
          <v-card v-bind="attrs" v-on="on" hover @click.stop="dialog = true" >
            <v-col>
              <v-row>
                <v-card-title v-model="tituloRut">{{ rutina.name }} <v-rating
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

                  <edit-rut></edit-rut>

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

                  <delete-confirmaticon></delete-confirmaticon>

                </div>
              </v-row>

            </v-col>
            <v-col class="text-left">
              <v-card-subtitle v-model="autorRut">Autor: {{ rutina.user.id }} </v-card-subtitle>
              <v-card-subtitle v-model="descripcionRut">Descripcion: {{ rutina.detail }}</v-card-subtitle>
<!--              <v-card-subtitle v-model="durRut">Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
            </v-col>


          </v-card>
        </v-container>

      </template>

      <v-card flat>
        <v-card-title>{{ rutina.name }}</v-card-title>
        <v-divider></v-divider>
        <v-card-subtitle></v-card-subtitle>
        <v-card-subtitle>Descripcion: {{ rutina.detail }}</v-card-subtitle>
<!--        <v-card-subtitle>Duracion: {{ rutina.durRut }}</v-card-subtitle>-->
        <v-container >
          <h4>Entrada en calor</h4>
          <p>Ejercicio</p>
        </v-container>
        <v-container >
          <h4>Ejercitacion</h4>
          <p>Ejercicio</p>
        </v-container>
        <v-container >
          <h4>Enfriamiento</h4>
          <p>Ejercicio</p>
        </v-container>


      </v-card>
    </v-dialog>
  </div>
</template>




<script>
import NuevaRutina from "@/components/nuevoEjercicio";
import  EditRutina from "@/components/editRut"
import EditRut from "@/components/editRut";
import DeleteConfirmaticon from "@/components/deleteConfirmation";

export default {
  components: {DeleteConfirmaticon, EditRut},
  methods: {
    data: function () {
      return {
        componets: {NuevaRutina, EditRutina},
        snackbar: false,
        headers: [
          {
            text: 'Ejericios',
            align: 'left',
            sortable: false,
            value: 'name',
          }
        ],
      }
    },
      newRutine: function (tituloRut, autorRut, descripcionRut, durRut, rating) {
        const newR = {tituloRut:tituloRut, autorRut: autorRut, descripcionRut: descripcionRut, durRut: durRut, rating: rating}
        this.data().rutinas.push(newR);
        console.log(this.data().rutinas);
        //console.log(JSON.stringify(this.data().rutinas));
      },
      showSnackbar: function (event) {
      event.stopPropagation();
      console.log(this);
      alert("PRUEBA");
        this.snackbar = true;
        setTimeout(() => {
          this.$emit("yourEvent");
        }, this.timeout);
      }
    },

    computed: {
      rutinas(){
        return this.$store.state.listaRutinas;
      }
    },

    mounted() {
      this.$store.dispatch("getRoutines");
    }
}
</script>