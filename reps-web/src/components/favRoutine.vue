<template>
  <div>
  <v-btn class="mt-3 mr-3" plain icon v-on:click="changeFav(rutina.id)" :color="getColour(rutina.id)">
    <v-icon>mdi-heart</v-icon>
  </v-btn></div>
</template>

<script>

export default {
  props: ['rutina', 'colour'],

  data(){
    return {

    }
  },
  methods: {
    getColour: function (id){
      if (this.idFavoritas.includes(id)){
        return "red"
      }
      else {
        return "grey"
      }
    },
    changeFav: function (id) {
      console.log("entra")
      console.log("le manda" + id)
      if (this.idFavoritas.includes(id)){
        this.$store.dispatch("deleteFavourites", id);
      }
      else {
        this.$store.dispatch("addFavourites", id);
      }
      //para actualizar los valores
      this.$store.dispatch('changeCardID');
    }
  },
  computed:{
    idFavoritas(){
      return this.$store.state.idFavoritas;
    }
  }
}
</script>