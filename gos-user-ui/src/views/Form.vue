<template>
    <v-container>
        <Form :form="form" class="pd-4" v-on:submit="submit" :submission="formData"/>
    </v-container>
</template>

<script>
  import {Form} from 'vue-formio';
  import axios from 'axios'

  export default {
    components: {
      Form
    },
    data() {
      return {
        form: {},
        formData: {
          data: {
          }
        }
      }
    },

    methods: {

      async updateCurrentForm() {

        let r = await axios.get('/api/task/gos', {
          auth: {
            username: 'user',
            password: 'user'
          }
        });

        this.task = r.data;
        this.formData.data = this.task.context;


        if (this.task) {
          let r = await axios.get('/api/form/' + this.task.name, {
            auth: {
              username: 'user',
              password: 'user'
            }
          });
          this.form = r.data;
        } else {
          this.form = undefined;
        }
      },

      async submit() {
        await axios.post('/api/task/' + this.task.id, this.formData.data, {
          auth: {
            username: 'user',
            password: 'user'
          }
        });
        this.updateCurrentForm();

      }
    },

    mounted() {
      this.updateCurrentForm();
    }
  }

</script>
