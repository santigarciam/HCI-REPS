<template>
  <div class="bg">
    <v-container fluid class="fill-height pa-0 ma-0">
      <v-row>
        <v-col>
          <v-row justify="center" align="end" style="height: 550px">
            <v-card   rounded color="rgb(0, 0, 0, 0.8)" elevation="0">
              <v-card-text>
                <v-card-title class="white--text">Oops! PAGE NOT FOUND. </v-card-title>
                <router-link to="/MisRutinas">
                  <div class="text-center">
                    <v-btn
                        to="/MisRutinas"
                        elevation="2"
                        color="#2679CC"
                        dark
                        x-large
                        rounded
                        class="mr-3"
                    >
                      VOLVER AL INICIO
                    </v-btn>
                  </div>
                </router-link>

              </v-card-text>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "NotFound"
}
</script>

<style scoped>
.bg {
  background-image: url("../assets/principal2.png");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
</style>