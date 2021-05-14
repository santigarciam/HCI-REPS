<template>
  <div>
  <v-btn
      icon
      plain
      color="grey"
      v-model="boton"
      class="mt-4 mr-2"
      v-on:click="showSnackbar"
  >
    <v-icon class="pb-3">mdi-share</v-icon>
  </v-btn>
  <v-snackbar
      v-model="snackbar"
  >¡Se ha copiado al clipboard el link de la rutina!</v-snackbar>
  </div>
</template>

<script>
export default {
  props: ['rutina'],

  data(){
    return {
      snackbar: false,
    }
  },

  methods:{
    showSnackbar: function (event) {
      event.stopPropagation();
      console.log(this);
      // alert("PRUEBA");
      this.snackbar = true;
      setTimeout(() => {
        this.$emit("yourEvent");
      }, this.timeout);
    }
  }
}
</script>