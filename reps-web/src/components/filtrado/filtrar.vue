<template>
  <v-col cols="2">
    <!--<v-dialog
        v-model="filterMenu"
        class="v-dialog"
        overlay-opacity="0"
        width="500"
        transition="slide-y-transition"
        bottom
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            outlined
            v-bind="attrs"
            v-on="on"
            depressed
            color="grey darken-2"
            @click.stop="drawer = !drawer"
        >
          Filtrar<v-icon class="ml-1" color="grey darken-2">mdi-menu-down</v-icon>
        </v-btn></template>
        <v-card>
          <div><form>
          <v-select
              v-model="value"
              :items="categorias"
              label="Categoria"
              outlined
              dense
              item-text="show"
              item-value="value"
              :menu-props="{ maxHeight: '400' }"
          ></v-select>-->
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

       <!-- </form></div></v-card>

    </v-dialog>-->
  </v-col>
</template>

<script>
import {bus} from "../../main";

export default {
  data: () => ({
    //drawer:false,
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