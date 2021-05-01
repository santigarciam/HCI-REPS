<template>
  <v-container fluid id="Favoritos">
    <v-row class="mt-2">
      <v-col>
        <v-row>
        <filtrar-por></filtrar-por>
        <order-by></order-by>
          <search-field></search-field>
          <v-spacer></v-spacer>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import filtrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
export default {
  name: "Favoritos",
  components: {SearchField, OrderBy, filtrarPor}
}
</script>

<style scoped>

</style>