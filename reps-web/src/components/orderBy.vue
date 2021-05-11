<template>
  <v-col cols="2">
  <v-select
      :items="orderBy"
      label="Ordenar por"
      v-model="selected"
      @input="ordenar"
      dense
      item-text="show"
      item-value="value"
      class="mr-3"
      outlined
  ></v-select>
    </v-col>
  <!--<v-menu offset-y transition="scroll-y-transition" bottom>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          outlined
          depressed
          class="ma-2"
          v-bind="attrs"
          v-on="on"
      >
        Ordenar
        <v-icon>mdi-chevron-down</v-icon>
      </v-btn>
    </template>
    <v-list>
      <v-list-item>
        <v-list-item-title>A-Z</v-list-item-title>
      </v-list-item>
      <v-list-item>
        <v-list-item-title>Z-A</v-list-item-title>
      </v-list-item>
      <v-list-item>
        <v-list-item-title>Puntuacion</v-list-item-title>
      </v-list-item>
      <v-list-item>
        <v-list-item-title>Duracion</v-list-item-title>
      </v-list-item>
    </v-list>

  </v-menu>-->
</template>

<script>
import {bus} from "../main";

export default {
  data: () => ({
    selected: "",
    orderBy: [
      {show:'Nombre', value:'name' },
      {show:'Descripción', value: 'detail' },
      {show:'Autor', value:'userId' },
      {show:'Fecha', value:'date'},
      {show:'Categoría', value:'categoryId'},
      {show:'Rating', value:'averageRating'},
      {show:'Dificultad', value:'difficulty'}]
  }),
methods:{
    ordenar: function (){
      bus.$emit('ordenar', this.selected)
    }
}


}
</script>

