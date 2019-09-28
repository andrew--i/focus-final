<template>
    <div style="height: 100%">
        <v-container>
            <v-row no-gutters :justify="'start'">
                <v-col  md="3">
                    <v-text-field

                            label="Форма"
                            v-model="formName"
                            single-line
                    ></v-text-field>

                </v-col>
                <v-col>

                </v-col>
            </v-row>


        </v-container>



        <div class="builder">
            <form-builder :form="form" :options="options"/>
        </div>
        <v-btn @click="saveForm">
            Сохранить форму
        </v-btn>
    </div>

</template>

<script>
  import {FormBuilder} from 'vue-formio'
  import axios from 'axios'

  export default {
    components: {
      FormBuilder
    },
    methods: {
      saveForm() {
        axios.post('/api/form/' + this.formName, JSON.stringify(this.form), {
          headers: {'Content-Type': 'application/json'},
          auth: {
            username: 'admin',
            password: 'admin'
          }
        });
      }
    },
    data() {
      return {
        form: {},
        formName: undefined,
        options: {
          builder: {
            basic: false,
            advanced: false,
            data: false,
            premium: false,
            customBasic: {
              title: 'Базовые компоненты',
              default: true,
              weight: 0,
              components: {
                textfield: true,
                textarea: true,
                email: true,
                phoneNumber: true,
                checkbox: true,
                number: true,
                datetime: true,
                address: true,
                select: true,
                file: true,
              }
            }
          }
        }
      }
    }
  }
</script>
