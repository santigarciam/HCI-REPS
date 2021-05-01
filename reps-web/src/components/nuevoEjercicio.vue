<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on, attrs }"> <!-- Por que hace falta esto -->
      <v-btn depressed class="ma-2 mr-4" slot="activator" v-bind="attrs" v-on="on" outlined>+Anadir</v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Agregar nuevo ejercicio</h2>
      </v-card-title>

      <v-card-text>
        <v-form class="px-3">
          <v-text-field label="Nombre" v-model="nombreRut"></v-text-field>
          <v-textarea label="Descripcion" v-model="descripcionRut" ></v-textarea>
          <v-text-field label="Materiales" v-model="durRut"></v-text-field>
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
  name: "nuevaRutina",
  data(){
    return{
      nombreRut:'',
      descripcionRut:'',
      durRut:''
    }
  },
  methods :{
    submit(){
      console.log(this.nombreRut,this.descripcionRut,this.durRut)
    },
  }
}
</script>

<style scoped>

</style>