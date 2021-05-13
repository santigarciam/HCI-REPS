<template>
<div>
  <v-text-field
      append-icon="mdi-magnify"
      class="mt-3"
      dense
      label="Buscar"
      clearable
      outlined
      v-model="busqueda"
      @click:append="buscar"
      @keyup.enter="buscar"
  ></v-text-field>
  <v-snackbar bottom color="error" v-model="error">
    <p> Para la búsqueda son necesarios al menos 3 caracteres. </p>
  </v-snackbar></div>
</template>


<script>
import {bus} from '../main'
export default {

  data (){
    return {
      busqueda: "",
      error: false
    }
  },

  methods: {
    buscar: function (){
      if (this.busqueda != null && this.busqueda.length < 3 && this.busqueda.length != 0 ){
        this.error = true;
      }
      else {
        if (this.busqueda == null){
          this.busqueda = ""
        }
        bus.$emit('busqueda' + this.$router.currentRoute.fullPath , this.busqueda)
      }
    }
  }
}
</script>
