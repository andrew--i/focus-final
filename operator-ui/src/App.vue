<template>
    <div id="app">
        <div id="nav">
            <router-link to="/">Главная</router-link>
            |
            <router-link to="/process">Конструктор процесса</router-link>
            |
            <router-link to="/builder">Конструтор формы</router-link>
            |
            <router-link to="/statistic">Статистика</router-link>
        </div>
        <router-view/>
    </div>
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
