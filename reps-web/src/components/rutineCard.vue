<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-container class = "container_v_card">
          <v-card  v-bind="attrs" v-on="on" >
            <v-col>
              <v-row>
                <v-card-title v-model="tituloRut">Rutina Pecho</v-card-title>
                <v-rating
                    v-model="rating"
                    background-color="grey"
                    color="orange"
                    medium
                ></v-rating>
                <v-spacer></v-spacer>
                <v-btn
                    icon
                    color="grey"
                    @click="snackbar = true"
                >
                  <v-icon>mdi-share</v-icon>
                </v-btn>
                <v-snackbar
                    v-model="snackbar"
                    :timeout="timeout"
                >
                  Se copio al clipboard el link de la rutina

                  <template v-slot:action="{ attrs }">
                    <v-btn
                        color="blue"
                        text
                        v-bind="attrs"
                        @click="snackbar = false"
                    >
                      Close
                    </v-btn>
                  </template>
                </v-snackbar>


            <v-btn
                    icon
                    color="pink"
                >
                  <v-icon>mdi-heart</v-icon>
                </v-btn>


            </v-row>

            </v-col>
            <v-col class="text-left">
             <v-card-subtitle v-model="autorRut">Autor: PONER EN AZUL  </v-card-subtitle>
            <v-card-subtitle v-model="descripcionRut">Descripcion:</v-card-subtitle>
            <v-card-subtitle v-model="durRut">Duracion:</v-card-subtitle>
            </v-col>


          </v-card>
        </v-container>

      </template>

      <v-card flat>
         <v-card-title> Rutina Pecho</v-card-title>
        <v-divider></v-divider>
        <v-card-subtitle></v-card-subtitle>
        <v-card-subtitle>Descripcion:</v-card-subtitle>
        <v-card-subtitle>Duracion:</v-card-subtitle>
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
export default {
  name: "rutineCard"
}
</script>

<style scoped>

</style>


<script>
export default {
  data () {
    return {
      headers: [
        {
          text: 'Ejericios',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        { text: 'Series', value: 'series' },
        { text: 'Repeticiones', value: 'reps' },
        { text: 'Peso (KG) ', value: 'peso' },

      ],
      tituloRut:'',
      autorRut:'',
      durRut:'',

    }
  }
}
</script>