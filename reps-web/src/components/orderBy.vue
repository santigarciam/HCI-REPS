<template>
  <v-col cols="2">
  <v-select
      :items="orderBy"
      label="Ordenar por"
      v-model="value"
      @click="ordenar"
      dense
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
export default {
  data: () => ({
    orderBy: ['Nombre', 'Descripción', 'Autor','Fecha', 'Categoría', 'Rating', 'Dificultad'],
    value: ['name', 'detail', 'userId', 'date', 'categoryId', 'averageRating', 'difficulty']
  }),



}
</script>

