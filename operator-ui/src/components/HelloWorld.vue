<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <p>
            Данное рабочее место реализует возможности
        </p>
        <ul>
            <li>
                <h3>Создание, редактирование бизнес-процесса ( гос. услуги )</h3>
            </li>
            <li>
                <h3>Создание, редактирование формы заявления клиента </h3>
            </li>
        </ul>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class HelloWorld extends Vue {
        @Prop() private msg!: string;
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
