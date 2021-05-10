<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-btn plain block class="ma-2" slot="activator" v-bind="attrs" v-on="on">Mi Perfil</v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Editar Perfil</h2>
      </v-card-title>

      <v-card-text>

        <v-form class="px-3">
          <v-container>
            <v-row>
              <v-col>
          <v-text-field outlined label="Nombre" v-model="nombreUsuario">nombre</v-text-field>
              </v-col>
              <v-col>
          <v-text-field outlined label="Apellido" v-model="apellidoUsuario" >apellido</v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field outlined label="Fecha De Nacimiento" v-model="fechaNacUsuario"></v-text-field>
              </v-col>
              <v-col>
                <v-text-field outlined label="Usuario" v-model="usuario" >usuario</v-text-field>
              </v-col>
            </v-row>

          <v-text-field outlined label="Contacto" v-model="durRut"></v-text-field>
          </v-container>



          <v-col>
            <v-row>
              <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
              <v-btn dark flat class="red mx-0" @click="submit">Cancelar</v-btn>
              <v-btn flat class="success mx-10" @click="submit">Guardar</v-btn>

            </v-row>
          </v-col>

        </v-form>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "perfilPopUp"
}
</script>

<style scoped>

</style>