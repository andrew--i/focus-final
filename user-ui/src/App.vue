<template>
    <v-app id="app">
        <div id="nav">
            <router-link to="/">Главная</router-link>
            |
            <router-link to="/process">Подать заявление</router-link>
            |
            <router-link to="/form">Формы для заполнения</router-link>
            |
            <router-link to="/response">Статус заявлений</router-link>
        </div>
        <router-view/>
    </v-app>
</template>

<style lang="scss">
    #app {
        font-family: 'Roboto', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        Height: 85%;
    }

    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
</style>
