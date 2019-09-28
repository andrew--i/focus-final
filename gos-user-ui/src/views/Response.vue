<template>
    <v-container>
        <v-data-table
                :headers="headers"
                :items="tasks"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>
    </v-container>
</template>

<script>

  import axios from 'axios'

  export default {
    data() {
      return {
        tasks: [],
        headers: [
          {
            text: 'Идентификатор',
            align: 'left',
            sortable: false,
            value: 'id',
          },
          {
            text: 'Текущий статус',
            align: 'left',
            sortable: false,
            value: 'name',
          },
          {
            text: 'Дата начала обработки',
            align: 'left',
            sortable: false,
            value: 'createTime',
          },
          {
            text: 'Контекст',
            align: 'left',
            sortable: false,
            value: 'context',
          },

        ]
      }
    },

    async mounted() {
      let r = await axios.get('/api/task', {
        auth: {
          username: 'user',
          password: 'user'
        }
      });

      this.tasks.splice(0, this.tasks.length);

      for (const task of r.data) {
        task.context = JSON.stringify(task.context);
        this.tasks.push(task);
      }

    }
  }
</script>
