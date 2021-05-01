<template>
  <v-menu offset-y transition="scroll-y-transition" bottom>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          outlined
          depressed
          class="ma-2"
          v-bind="attrs"
          v-on="on"
      >
        Ordenar
      </v-btn>
    </template>
    <v-list>
      <v-list-item
          v-for="n in 5"
          :key="n"
          link
      >
        <v-list-item-title v-text="'Opcion ' + n"></v-list-item-title>
      </v-list-item>
    </v-list>

  </v-menu>
</template>



