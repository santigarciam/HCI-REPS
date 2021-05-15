<template>
  <div class="noRut " >
        <v-row>
          <v-progress-linear
              background-color="#00B2EB"
              color="#2679CC"
              height="4"
              indeterminate
              rounded
          ></v-progress-linear>
          <v-col></v-col>
        </v-row>
        <v-row
            align="center"
            justify="center"
        >
          <v-col class="text-center">
            <h1 class="display-1 mb-4">
              <span>{{mensaje1}}</span>
              <span class="colorReps boldText">{{ mensaje2 }}</span>
            </h1>
            <h4 class="font-weight-regular subheading">
                {{subtitulo}}
            </h4>
          </v-col>
        </v-row>
        <v-row>
          <v-col></v-col>
          <v-progress-linear
              background-color="#00B2EB"
              color="#2679CC"
              height="4"
              indeterminate
              rounded
          ></v-progress-linear>
        </v-row>
  </div>
</template>

<script>
export default {
  props:['mensaje1', 'mensaje2', 'subtitulo'],

}
</script>

<style scoped>
.boldText{
  font-weight:bold
}
.colorReps{
  color: #2679CC ;
}
</style>