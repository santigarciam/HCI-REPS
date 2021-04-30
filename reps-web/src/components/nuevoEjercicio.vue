<template>
  <v-card flat>
    <v-card-title >Rutina Pecho</v-card-title>

    <v-text-field
        ref="nombreRut" v-mode="name"
        :rules="[() => !!nombreRut || 'Rutina Brazos']"
        label="Nombre *"
        placeholder="Rutina Brazos"
        required
    ></v-text-field>

  </v-card>
</template>

<script>
export default {
  name: "nuevoEjercicio"
}
</script>

<style scoped>

</style>