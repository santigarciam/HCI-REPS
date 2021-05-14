<template>
  <v-dialog persistent v-model="dialogEditRut" width="800px">
    <template  v-slot:activator="{ on, attrs }">
      <v-btn icon class="mt-4 mr-2" plain color = "grey" slot="activator" small  v-bind="attrs" v-on="on">
        <v-icon>
          mdi-pencil
        </v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title>Editar Ejercicio</v-card-title>

      <v-card-text>
        <v-form class="px-3">
          <v-text-field label="Nombre*" v-model="nombreRut"></v-text-field>
          <v-textarea label="Descripcion*" v-model="descripcionRut" ></v-textarea>
          <v-text-field label="Materiales*" v-model="durRut"></v-text-field>
          <v-file-input
              v-model="files"
              placeholder="Subi tu demostracion"
              label="Demostracion"
              multiple
              prepend-icon="mdi-paperclip"
          >
            <template v-slot:selection="{ text }">
              <v-chip
                  small
                  label
                  color="primary"
              >
                {{ text }}
              </v-chip>
            </template>
          </v-file-input>
        </v-form>
      </v-card-text>


    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "editEj",
  data(){
    return{
      nombreRut:'',
      descripcionRut:'',
      durRut:''
    }
  },
}
</script>

<style scoped>

</style>