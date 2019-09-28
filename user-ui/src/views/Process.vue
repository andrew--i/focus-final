<template>
    <div style="height: 100%">
        <v-btn @click="startProcess" :disabled="disable">
            Регистрация брака
        </v-btn>
    </div>
</template>

<script>

  import axios from 'axios'

  export default {
    methods: {

      async startProcess() {
        await axios.post('/api/process/instance/zags_process', {}, {
          auth: {
            username: 'user',
            password: 'user'
          }
        });

        await this.$router.push('/form');

        this.updateCanRunProcess();
      },

      async updateCanRunProcess() {
        let r = await axios.get('/api/process/instance/zags_process', {
          auth: {
            username: 'user',
            password: 'user'
          }
        });

        this.disable = r.data ;
      }

    },

    data() {
      return {
        disable: false
      }
    },

    mounted() {
      this.updateCanRunProcess();
    }
  }
</script>

<style>

</style>
