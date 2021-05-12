<template>
  <v-col cols="2">
    <v-select
        v-model="value"
        label="Filtrar"
        multiple
        outlined
        dense
        :items="filters"
        deletable-chips
        :menu-props="{ maxHeight: '400' }"
    >
    </v-select>
  </v-col>
</template>

<script>
export default {
  data: () => ({
    filters: ['Foo', 'Bar', 'Fizz', 'Buzz', 'Otro'],
    categorias:['1', '2', '3']
  }),
}
</script>