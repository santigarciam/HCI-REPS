<template>

      <v-container >
        <v-row v-for="(item,i) in 2"
               :key="i" class="pb-8" no-gutters justify="center">
          <v-col cols="10">
            <v-expansion-panels>
              <v-expansion-panel >
                <v-expansion-panel-header class="mb-2"><h3>Nombre Rutina</h3></v-expansion-panel-header>
                <div class="pl-8">
                <p>Duracion: ...</p>
                <p>Descripcion: ...</p>
                <p>Materiales: ...</p>
                </div>
                <v-expansion-panel-content>
                  <v-data-table
                      :headers="headers"
                      :items="rutinas"
                      class="elevation-1"
                  >
                    <template v-slot:items="props">
                      <td>{{ props.item.name }}</td>
                      <td class="text-xs-right">{{ props.item.repeticiones }}</td>
                      <td class="text-xs-right">{{ props.item.peso }}</td>
                      <td class="text-xs-right">{{ props.item.pausa }}</td>
                    </template>
                  </v-data-table>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col>
          <v-col>
            <v-btn rounded plain>
              <v-icon color="darck">mdi-delete</v-icon>
            </v-btn>
            <v-btn rounded plain >
              <v-icon color="grey">mdi-pencil</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
</template>

<script>
export default {
  name: "rutineCard"
}
</script>

<style scoped>

</style>


<script>
export default {
  data () {
    return {
      headers: [
        {
          text: 'Ejericios',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        { text: 'Series', value: 'series' },
        { text: 'Repeticiones', value: 'reps' },
        { text: 'Peso (KG) ', value: 'peso' },

      ],
      rutinas: [
      ]
    }
  }
}
</script>