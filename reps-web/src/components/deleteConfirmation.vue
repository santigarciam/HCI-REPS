<template>
  <v-dialog v-model="deleteConfi" width="800px">
    <template  v-slot:activator="{ on, attrs }">
      <v-btn plain color = "red" slot="activator" small  v-bind="attrs" v-on="on">
        <v-icon>
          mdi-delete
        </v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title>Estas seguro que quiere borrar?</v-card-title>
      <v-col text--center>
        <v-row>
          <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
          <v-btn flat class="red mx-0" @click="submit">No</v-btn>
          <v-btn flat class="success mx-10" @click="submit">Si</v-btn>

        </v-row>
      </v-col>

      <v-col></v-col>
    </v-card>


  </v-dialog>

</template>

<script>
export default {
  name: "deleteConfirmaticon"
}
</script>

<style scoped>

</style>