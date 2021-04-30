<template>
  <div class="MisRutinas">
    <v-container>
      <v-item-group>

      <v-col class="text-right">
        <v-row class="text-right">
          <h1 > Mis Rutinas</h1> <!-- Como lo centro -->
          <v-spacer></v-spacer>
        <nueva-rutina></nueva-rutina>
        </v-row>
      </v-col>
      </v-item-group>
    <p class="text-center">Crear rutinas para sus alumnos!</p>
      <v-spacer></v-spacer>
    </v-container>

    <v-divider></v-divider>
<v-col>
    <v-row>
    <v-menu transition="scroll-y-transition">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
           depressed
            class="ma-2"
            v-bind="attrs"
            v-on="on"
        >
          Filtrar
        </v-btn>
      </template>
      <v-list>
        <v-list-item
            v-for="n in 5"
            :key="n"
            link
        >
          <v-list-item-title v-text="'Opcion ' + n"></v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
      <v-menu transition="scroll-y-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              depressed
              class="ma-2"
              v-bind="attrs"
              v-on="on"
          >
            Ordenar
          </v-btn>
        </template>
        <v-list>
          <v-list-item
              v-for="n in 5"
              :key="n"
              link
          >
            <v-list-item-title v-text="'Opcion ' + n"></v-list-item-title>
          </v-list-item>
        </v-list>

      </v-menu>

<!--      <v-toolbar-->
<!--          dense-->
<!--          floating-->
<!--          small-->
<!--      >-->
<!--        <v-text-field-->
<!--            small-->
<!--            hide-details-->
<!--            prepend-icon="mdi-magnify"-->
<!--        ></v-text-field>-->
<!--      </v-toolbar>-->


    </v-row>
</v-col>

    <rutine-card></rutine-card>
  </div>
</template>

<script>

import RutineCard from "@/components/rutineCard";
import NuevaRutina from "@/components/nuevaRutina";
export default {
  name: "MisRutinas",
  components: {
    NuevaRutina,
    RutineCard
  },
};
</script>

<style scoped>
.container_v_card{
  /*margin: auto;*/
  /*width:75%;*/
  /*height:50%;*/
}
.MisRutinas{
  margin: auto ;

}
</style>