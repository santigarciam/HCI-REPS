<template>
  <v-col cols="2">
  <v-select
      v-model="value"
      :items="filters"

      label="Filtrar"
      multiple
      outlined
      dense
      :menu-props="{ maxHeight: '400' }"
  ></v-select>
    </v-col>
<!--  <v-container>-->
  <!--<v-menu offset-y transition="scroll-y-transition" bottom>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          outlined
          depressed
          class="ma-2 ml-4"
          v-bind="attrs"
          v-on="on"
      >
        Filtrar
        <v-icon>mdi-chevron-down</v-icon>
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
  </v-menu>-->

<!--  <v-row>-->

<!--    <v-col cols="2">-->

<!--      <v-select :items="filters" @click="update"  label="Filtrar"-->

<!--                dense outlined v-model="difficulty" item-text="spanish" item-value="id"></v-select>-->

<!--    </v-col>-->

<!--    <v-col cols="2">-->

<!--      <v-select :items="orderBy" @click="update" label="Ordenar por"-->

<!--                dense outlined v-model="order" item-text="spanish" item-value="id"></v-select>-->

<!--    </v-col>-->

<!--    <v-col cols="4">-->

<!--      <v-text-field-->

<!--          append-icon="mdi-magnify"-->

<!--          flat-->

<!--          dense-->

<!--          rounded-->

<!--          outlined-->

<!--          label="Buscar"-->

<!--          solo-->

<!--          clearable-->

<!--      ></v-text-field>-->

<!--    </v-col>-->



<!--    <v-spacer></v-spacer>-->

<!--    <v-btn-->

<!--        outlined-->

<!--        class="mr-5 mt-3"-->

<!--    >-->

<!--      + Añadir-->

<!--    </v-btn>-->

<!--  </v-row>-->
<!--</v-container>-->
</template>

<script>
export default {
  data: () => ({
  filters: ['Foo', 'Bar', 'Fizz', 'Buzz', 'Otro'],
 }),
}
</script>