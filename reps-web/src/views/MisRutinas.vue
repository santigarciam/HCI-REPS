<template>
  <div class="MisRutinas">
    <v-divider></v-divider>
<v-col>
    <v-row class="mt-2">
    <filtrar-por></filtrar-por>
      <order-by></order-by>
      <search-field></search-field>
      <v-spacer></v-spacer>
      <nueva-rutina></nueva-rutina>


    </v-row>
</v-col>

    <rutine-card :key="cardID"></rutine-card>
  </div>
</template>

<script>

import RutineCard from "@/components/rutineCard";
import NuevaRutina from "@/components/nuevaRutina";
import FiltrarPor from "@/components/filtrarPor";
import OrderBy from "@/components/orderBy";
import SearchField from "@/components/searchField";

export default {
  name: "MisRutinas",
  components: {
    SearchField,
    OrderBy,
    FiltrarPor,
    NuevaRutina,
    RutineCard
  },
  mounted() {
    this.$store.dispatch("changeCardID");
  },
  computed: {
    cardID(){
      return this.$store.state.cardID;
    }
  }
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