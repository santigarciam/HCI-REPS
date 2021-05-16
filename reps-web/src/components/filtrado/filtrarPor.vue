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
</template>

<script>
export default {
  data: () => ({
  filters: ['Foo', 'Bar', 'Fizz', 'Buzz', 'Otro'],
 }),
}
</script>