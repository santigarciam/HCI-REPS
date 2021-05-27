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
</template>

<script>
import {bus} from "../../main";

export default {
  data: () => ({
    selected: "",
    orderBy: [
      {show:'Nombre', value:'name' },
      {show:'Descripción', value: 'detail' },
      {show:'Autor', value:'userId' },
      {show:'Fecha', value:'date'},
      {show:'Categoría', value:'categoryId'},
      {show:'Puntuación', value:'averageRating'},
      {show:'Dificultad', value:'difficulty'}]
  }),
methods:{
    ordenar: function (){
      bus.$emit('ordenar' + this.$router.currentRoute.fullPath, this.selected)
    }
}

}
</script>

