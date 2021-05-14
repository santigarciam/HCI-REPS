<template>
  <v-col cols="2">
    <v-btn
        outlined
        depressed
        color="grey darken-2"
        @click.stop="drawer = !drawer"
    >
      Filtrar<v-icon class="ml-1" color="grey darken-2">mdi-menu-down</v-icon>
    </v-btn>
    <v-menu

    >
      <v-list
          dense
          rounded
      >
        <v-list-item>
          <v-select
              v-model="value"
              :items="categorias"
              label="Categoria"
              outlined
              dense
              item-text="show"
              item-value="value"
              :menu-props="{ maxHeight: '400' }"
          ></v-select> </v-list-item>
         <v-list-item> <v-select
              v-model="value"
              :items="dificultad"
              label="Dificultad"
              outlined
              item-text="show"
              item-value="value"
              dense
              :menu-props="{ maxHeight: '400' }"
          ></v-select></v-list-item>

      </v-list>
    </v-menu>
  </v-col>
</template>

<script>
export default {
  data: () => ({
    drawer:false,
    dificultad: [
      {show:'', value:'rookie' },
      {show:'Novato', value:'rookie' },
      {show:'Principiante', value: 'beginner' },
      {show:'Intermedio', value:'intermediate' },
      {show:'Avanzado', value:'advanced'},
      {show:'Experto', value:'expert'},],
    categorias: [
      {show:'Novato', value:'rookie' },
      {show:'Principiante', value: 'beginner' },
      {show:'Intermedio', value:'intermediate' },
      {show:'Avanzado', value:'advanced'},
      {show:'Experto', value:'expert'},]
  }),
}
</script>