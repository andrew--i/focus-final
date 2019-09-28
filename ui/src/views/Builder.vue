<template>
    <div class="builder">
        <button @click="saveForm">Сохранить</button>
        <form-builder :form="form" :options="options"/>
    </div>
</template>

<script>
  import {FormBuilder} from 'vue-formio'

  export default {
    components: {
      FormBuilder
    },
    methods: {
      saveForm() {
        console.log(JSON.stringify(this.form))
      }
    },
    data() {
      return {
        form: {},
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
