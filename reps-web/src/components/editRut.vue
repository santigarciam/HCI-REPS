<template>
  <v-dialog v-model="dialogEditRut" width="800px">
    <template  v-slot:activator="{ on, attrs }">
      <v-btn plain color = "grey" slot="activator" small  v-bind="attrs" v-on="on">
        <v-icon>
          mdi-pencil
        </v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title>
        <h2>Editar Rutina</h2>
      </v-card-title>

      <v-card-text>
        <v-form class="px-3">
          <v-text-field label="Nombre*" v-model="nombreRut"></v-text-field>
          <v-textarea label="Descripcion*" v-model="descripcionRut" ></v-textarea>
          <v-text-field label="Duracion*" v-model="durRut"></v-text-field>

          <v-col>
            <v-row>
              <v-card-title>
                Ciclos de entrenamiento
              </v-card-title>
              <v-menu transition="scroll-y-transition">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                      plain
                      class="ma-2"
                      v-bind="attrs"
                      v-on="on"
                  >
                    Ciclo
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item link>
                    <v-list-item-title>Entrada en calor</v-list-item-title>
                  </v-list-item>
                </v-list>
                <v-list>
                  <v-list-item link>
                    <v-list-item-title>Ejercitacion</v-list-item-title>
                  </v-list-item>
                </v-list>
                <v-list>
                  <v-list-item link>
                    <v-list-item-title>Enfriamiento</v-list-item-title>
                  </v-list-item>
                </v-list>

              </v-menu>
            </v-row>
          </v-col>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field label="Repeticiones:" v-model="repsEj"></v-text-field>
              </v-col>
              <v-col>
              <v-text-field label="Descanso Entre Repeticiones:" v-model="descReps"></v-text-field>
              </v-col>
              <v-col>
                <v-text-field label="Descanso Entre Ejercicios:" v-model="descReps"></v-text-field>
              </v-col>
            </v-row>
          </v-container>

<!--            <v-col>-->
<!--              <v-row>-->
<!--              <v-text-field label="Repeticiones:" v-model="repsEj"></v-text-field>-->
<!--              <v-text-field label="Descanso Entre Repeticiones:" v-model="descReps"></v-text-field>-->
<!--             <v-text-field label="Descanso Entre Ejercicios::" v-model="descEjs"></v-text-field>-->
<!--              </v-row>-->
<!--            </v-col>-->


          <v-col>
            <v-row>
              <v-spacer></v-spacer> <!-- VER SI SE PUEDE SACAR ESTO Y MOVERLO CON CSS -->
              <v-btn flat class="red mx-0" @click="submit">Cancelar</v-btn>
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
  name: "editRut"
}
</script>

<style scoped>

</style>