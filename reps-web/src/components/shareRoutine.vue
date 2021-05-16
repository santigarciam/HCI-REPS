<template>
  <div>
  <v-btn
      icon
      plain
      color="grey"
      v-model="boton"
      class="mt-4 mr-2"
      v-on:click="copyAndShowSnackBar"

      value="HOLA"
      id="myInput"
  >
    <v-icon class="pb-3">mdi-share</v-icon>
  </v-btn>
<!--    <input  type="text" value="Hello World" id="myInput">-->
  <v-snackbar
      v-model="snackbar"
  >¡Se ha copiado al portapapeles el link de la rutina!</v-snackbar>
  </div>
</template>

<script>

import {router} from "../main";

export default {
  props: ['rutina'],

  data(){
    return {
      snackbar: false,
      testingCode:"1234"
    }
  },


  methods:{
    copyAndShowSnackBar: async function (event) {
      event.stopPropagation();
      console.log(router);

      await navigator.clipboard.writeText(`http://localhost:8083/#/routines/${this.rutina.id}`); //// AVISAR QUE CAMBIEN ESTA URL PARA Q FUNCIONEE

     //  alert("PRUEBA");
      this.snackbar = true;
      setTimeout(() => {
        this.$emit("yourEvent");
      }, this.timeout);
    },


  },
  }


</script>