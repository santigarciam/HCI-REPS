<template>
  <v-col cols="2">
          <v-select
              v-model="selected"
              :items="dificultad"
              label="Dificultad"
              outlined
              @input="filtrarDificultad()"
              item-text="show"
              item-value="value"
              dense
              :menu-props="{ maxHeight: '400' }"
          ></v-select>
  </v-col>
</template>

<script>
import {bus} from "../../main";

export default {
  data: () => ({
    selected: "",
    dificultad: [
      {show:'Todas', value:'' },
      {show:'Novato', value:'rookie' },
      {show:'Principiante', value: 'beginner' },
      {show:'Intermedio', value:'intermediate' },
      {show:'Avanzado', value:'advanced'},
      {show:'Experto', value:'expert'},],
  }),
  methods:{
    filtrarDificultad: function (){
      bus.$emit('dificultad' + this.$router.currentRoute.fullPath, this.selected)
    }
  }
}
</script>

<style scoped>
.v-dialog {
  position: absolute;
  bottom: 0;
  right: 0;
}
</style>