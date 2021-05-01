<template>
  <v-container fluid id="Descubrir">
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


import FiltrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";
export default {
  name: "Descubrir",
  components: {SearchField, OrderBy, FiltrarPor}
}
</script>

<style scoped>

</style>