import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './styles.scss';


import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify);

let vue = new Vue({
    router,
    store,
    render: (h) => h(App),
});
vue.$mount('#app');
