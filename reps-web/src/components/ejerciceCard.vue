<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-card depressed class="ma-2" slot="activator" v-bind="attrs" v-on="on">
        <v-col>
          <v-row>
            <v-card-title v-model="nombreEj">Flexiones</v-card-title>
            <v-spacer></v-spacer>
            <v-btn
                icon
                color="grey"

            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn
                icon
                color="red"
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>


          </v-row>
        </v-col>

      </v-card>
    </template>

    <v-card>
      <v-card-title>
        <h2>{{this.nombreEj}}</h2>
      </v-card-title>

      <v-card-text>
        <v-card-title v-model="descripcionEj">Descripcion:</v-card-title>
        <v-card-title v-model="durEj">Duracion:</v-card-title>
        <v-card-title v-model="multEj">Multimedia:</v-card-title>
        <v-card-title v-model="equipEj">Equipamiento:</v-card-title>
      </v-card-text>


    </v-card>
  </v-dialog>
</template>

<script>
export  default {
data(){
  return{
    nombreEj:''
  }
}
}

</script>