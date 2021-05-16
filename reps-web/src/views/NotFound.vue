<template>
  <div class="bg">
    <v-container fluid class="fill-height pa-0 ma-0">
      <v-row>
        <v-col>
          <v-row justify="center" align="center" style="height: 550px">
            <v-card   rounded color="rgb(0, 0, 0, 0.8)" elevation="0">
              <v-card-text>
                <v-card-title class="white--text justify-center">Oops</v-card-title>
                <v-card-text class="white--text justify-center">Esta página no está disponible</v-card-text>
                <router-link to="/MisRutinas">
                  <div class="text-center">
                    <v-btn
                        to="/MisRutinas"
                        elevation="2"
                        color="#2679CC"
                        dark
                        x-large
                        rounded
                        class="mr-3 botonVolver"
                    >
                      VOLVER
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
a { text-decoration: none; }

.bg {
  background-image: url("../assets/principal2.png");
  height: 100%;
  background-position: top;
  background-repeat: no-repeat;
  background-size: cover;
}
.botonVolver{

}
</style>